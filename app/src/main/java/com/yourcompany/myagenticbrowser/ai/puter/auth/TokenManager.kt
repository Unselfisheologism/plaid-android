// FILE: app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/TokenManager.kt
package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.SharedPreferences
import java.time.Instant

class TokenManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("secure_puter_auth_prefs", Context.MODE_PRIVATE)
    }
    
    companion object {
        private const val ACCESS_TOKEN_KEY = "puter_auth_token"
        private const val TOKEN_EXPIRY_KEY = "token_expiration"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val AUTH_STATUS_KEY = "is_authenticated"
    }

    /**
     * Saves the authentication token and its expiry time
     */
    fun saveToken(accessToken: String, expiresIn: Long = 3600) {
        val expiryTime = System.currentTimeMillis() + (expiresIn * 1000)
        
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
        
        return if (token != null && System.currentTimeMillis() < expiryTime) {
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

    /**
     * Returns the SharedPreferences editor for direct access if needed
     */
    fun getPreferencesEditor() = sharedPreferences.edit()
    
    /**
     * Sets the authentication status
     */
    fun setAuthStatus(isAuthenticated: Boolean) {
        sharedPreferences.edit()
            .putBoolean(AUTH_STATUS_KEY, isAuthenticated)
            .apply()
    }
    
    /**
     * Gets the authentication status
     */
    fun getAuthStatus(): Boolean {
        return sharedPreferences.getBoolean(AUTH_STATUS_KEY, false)
    }
}