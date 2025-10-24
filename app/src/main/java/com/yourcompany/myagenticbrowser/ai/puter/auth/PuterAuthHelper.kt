package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.yourcompany.myagenticbrowser.R
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

import com.yourcompany.myagenticbrowser.ai.puter.auth.TokenManager

class PuterAuthHelper(private val context: Context) {
    private val tokenManager = TokenManager(context)

    fun isAuthenticated(): Boolean {
        return tokenManager.hasValidToken()
    }

    fun getAuthToken(): String? {
        return tokenManager.getValidToken()
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
            .apply {
                // Use a proper close button icon from your resources if available
                getCloseButtonIcon()?.let { icon ->
                    setCloseButtonIcon(icon)
                }
            }
            .build()
        
        customTabsIntent.launchUrl(context, Uri.parse(authUrl))
    }
    
    private fun getCloseButtonIcon(): Bitmap? {
        // In a real implementation, you'd load an actual icon from resources
        return null
    }

    fun clearAuthentication() {
        tokenManager.clearTokens()
    }

    fun needsTokenRefresh(): Boolean {
        // Check if token will expire within the next 5 minutes
        // Use TokenManager to get expiration time
        return tokenManager.needsTokenRefresh()
    }

    fun refreshTokenIfNeeded(onComplete: (Boolean) -> Unit) {
        if (!needsTokenRefresh() || !isAuthenticated()) {
            onComplete(false)
            return
        }

        // Get refresh token using TokenManager
        val refreshToken = tokenManager.getRefreshToken() ?: run {
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
                            val refreshToken = json.optString("refresh_token", null) // Get refresh token if available
                            
                            if (refreshToken != null) {
                                // Save with refresh token if available
                                tokenManager.saveTokenWithRefresh(token, refreshToken, expiresIn)
                            } else {
                                // Save without refresh token
                                tokenManager.saveToken(token, expiresIn)
                            }
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
    
    companion object {
        const val AUTH_STATUS_CHANGED = "com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED"
    }
}