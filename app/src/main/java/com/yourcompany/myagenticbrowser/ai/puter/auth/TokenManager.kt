// FILE: app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/TokenManager.kt
package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.SharedPreferences
import java.time.Instant

class TokenManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("puter_auth_prefs", Context.MODE_PRIVATE)
    
    companion object {
        private const val ACCESS_TOKEN_KEY = "puter_access_token"
        private const val TOKEN_EXPIRY_KEY = "token_expiry"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }

    /**
     * Saves the authentication token and its expiry time
     */
    fun saveToken(accessToken: String, expiresIn: Long = 3600) {
        val expiryTime = Instant.now().plusSeconds(expiresIn).epochSecond
        
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN_KEY, accessToken)
            .putLong(TOKEN_EXPIRY_KEY, expiryTime)
            .apply()
    }

    /**
     * Gets the current access token if it's still valid
     */
    fun getValidToken(): String? {
        val token = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        val expiryTime = sharedPreferences.getLong(TOKEN_EXPIRY_KEY, 0)
        
        return if (token != null && Instant.now().epochSecond < expiryTime) {
            token
        } else {
            null
        }
    }

    /**
     * Checks if a valid token exists
     */
    fun hasValidToken(): Boolean {
        return getValidToken() != null
    }

    /**
     * Clears all authentication tokens
     */
    fun clearTokens() {
        sharedPreferences.edit()
            .remove(ACCESS_TOKEN_KEY)
            .remove(TOKEN_EXPIRY_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .apply()
    }
}