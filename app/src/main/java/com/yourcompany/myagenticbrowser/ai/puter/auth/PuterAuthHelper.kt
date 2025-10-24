package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.yourcompany.myagenticbrowser.R
import java.security.GeneralSecurityException

class PuterAuthHelper(private val context: Context) {
    private val sharedPreferences: SharedPreferences
    
    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        
        sharedPreferences = EncryptedSharedPreferences.create(
            "secure_puter_auth_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun isAuthenticated(): Boolean {
        val token = getAuthToken() ?: return false
        val expiration = getTokenExpiration()
        return System.currentTimeMillis() < expiration
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString("puter_auth_token", null)
    }

    private fun getTokenExpiration(): Long {
        return sharedPreferences.getLong("token_expiration", 0)
    }

    fun launchAuthentication() {
        val clientId = "myagenticbrowser"
        val redirectUri = "myagenticbrowser://auth"
        val authUrl = "https://puter.com/api/auth?" +
                "client_id=$clientId&" +
                "redirect_uri=${Uri.encode(redirectUri)}&" +
                "response_type=code&" +
                "scope=read+write" // Added proper scope parameter

        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(context.getColor(R.color.purple_500))
            .setCloseButtonIcon(
                // Use a proper close button icon from your resources
                getCloseButtonIcon()
            )
            .build()
        
        customTabsIntent.launchUrl(context, Uri.parse(authUrl))
    }
    
    private fun getCloseButtonIcon(): android.graphics.Bitmap? {
        // In a real implementation, you'd load an actual icon from resources
        return null
    }

    fun clearAuthentication() {
        sharedPreferences.edit()
            .remove("puter_auth_token")
            .remove("token_expiration")
            .apply()
    }

    fun needsTokenRefresh(): Boolean {
        // Check if token will expire within the next 5 minutes
        val expiration = getTokenExpiration()
        return expiration > 0 && System.currentTimeMillis() > (expiration - 300000)
    }

    fun refreshTokenIfNeeded(onComplete: (Boolean) -> Unit) {
        if (!needsTokenRefresh() || !isAuthenticated()) {
            onComplete(false)
            return
        }

        val refreshToken = sharedPreferences.getString("refresh_token", null) ?: run {
            onComplete(false)
            return
        }

        val client = okhttp3.OkHttpClient()
        val json = """{
            "refresh_token": "$refreshToken",
            "client_id": "myagenticbrowser",
            "grant_type": "refresh_token"
        }""".trimIndent()
        
        val requestBody = json.toRequestBody("application/json".toMediaType())
        
        val request = okhttp3.Request.Builder()
            .url("https://puter.com/api/token")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                onComplete(false)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    try {
                        val responseBody = response.body?.string()
                        if (!responseBody.isNullOrEmpty()) {
                            val json = org.json.JSONObject(responseBody)
                            val token = json.getString("access_token")
                            val expiresIn = json.optLong("expires_in", 3600)
                            
                            saveToken(token, expiresIn)
                            onComplete(true)
                            return
                        }
                    } catch (e: Exception) {
                        // Fall through to failure
                    }
                }
                onComplete(false)
            }
        })
    }

    private fun saveToken(token: String, expiresIn: Long) {
        val editor = sharedPreferences.edit()
        editor.putString("puter_auth_token", token)
        editor.putLong("token_expiration", System.currentTimeMillis() + (expiresIn * 1000))
        
        // Save refresh token if provided
        try {
            val json = org.json.JSONObject(token) // This would need to be adjusted based on actual response
            val refreshToken = json.optString("refresh_token", "")
            if (refreshToken.isNotEmpty()) {
                editor.putString("refresh_token", refreshToken)
            }
        } catch (e: Exception) {
            // Not a JSON token or doesn't contain refresh token
        }
        
        editor.apply()
    }
    
    companion object {
        const val AUTH_STATUS_CHANGED = "com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED"
    }
}