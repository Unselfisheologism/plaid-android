// FILE: app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/TokenManager.kt
package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class TokenManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        try {
            val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            EncryptedSharedPreferences.create(
                "secure_puter_auth_prefs",
                "prefs_key",
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            // Fallback to regular SharedPreferences if encryption fails
            context.getSharedPreferences("secure_puter_auth_prefs", Context.MODE_PRIVATE)
        }
    }
    
    companion object {
        private const val ACCESS_TOKEN_KEY = "puter_auth_token"
        private const val TOKEN_EXPIRY_KEY = "token_expiration"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val AUTH_STATUS_KEY = "is_authenticated"
    }

    /**
     * Saves the authentication token with a reasonable expiration for mobile session
     * Puter.js returns the token directly - no exchange or refresh tokens needed
     */
    fun saveToken(accessToken: String, expiresIn: Long = 86400) { // 24 hours default (not 1 hour)
        val expiryTime = System.currentTimeMillis() + (expiresIn * 1000)
        
        try {
            sharedPreferences.edit()
                .putString(ACCESS_TOKEN_KEY, accessToken)
                .putLong(TOKEN_EXPIRY_KEY, expiryTime)
                .putBoolean(AUTH_STATUS_KEY, true)
                .apply()
        } catch (e: Exception) {
            android.util.Log.e("TokenManager", "Failed to save token", e)
        }
    }

    /**
     * Gets the current access token if it's still valid
     */
    fun getValidToken(): String? {
        return try {
            val token = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
            val expiryTime = sharedPreferences.getLong(TOKEN_EXPIRY_KEY, 0)
            
            if (!token.isNullOrEmpty() && System.currentTimeMillis() < expiryTime) {
                token
            } else {
                null
            }
        } catch (e: Exception) {
            android.util.Log.e("TokenManager", "Failed to read token", e)
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
        try {
            sharedPreferences.edit()
                .remove(ACCESS_TOKEN_KEY)
                .remove(TOKEN_EXPIRY_KEY)
                .putBoolean(AUTH_STATUS_KEY, false)
                .apply()
        } catch (e: Exception) {
            android.util.Log.e("TokenManager", "Failed to clear tokens", e)
        }
    }
    
    /**
     * Gets the authentication status
     */
    fun getAuthStatus(): Boolean {
        return try {
            sharedPreferences.getBoolean(AUTH_STATUS_KEY, false) && hasValidToken()
        } catch (e: Exception) {
            android.util.Log.e("TokenManager", "Failed to get auth status", e)
            false
        }
    }

    /**
     * Checks if the token needs refresh (will expire in less than 5 minutes)
     */
    fun needsTokenRefresh(): Boolean {
        return try {
            val expiryTime = sharedPreferences.getLong(TOKEN_EXPIRY_KEY, 0)
            val fiveMinutesInMillis = 5 * 60 * 1000L // 5 minutes in milliseconds
            val willExpireSoon = System.currentTimeMillis() + fiveMinutesInMillis >= expiryTime
            
            willExpireSoon && hasValidToken()
        } catch (e: Exception) {
            android.util.Log.e("TokenManager", "Failed to check if token needs refresh", e)
            false
        }
    }

    /**
     * Gets the refresh token
     */
    fun getRefreshToken(): String? {
        return try {
            sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
        } catch (e: Exception) {
            android.util.Log.e("TokenManager", "Failed to read refresh token", e)
            null
        }
    }

    /**
     * Saves token with refresh token
     */
    fun saveTokenWithRefresh(accessToken: String, refreshToken: String?, expiresIn: Long = 3600) {
        val expiryTime = System.currentTimeMillis() + (expiresIn * 1000)
        
        try {
            val editor = sharedPreferences.edit()
            editor.putString(ACCESS_TOKEN_KEY, accessToken)
            editor.putLong(TOKEN_EXPIRY_KEY, expiryTime)
            if (refreshToken != null) {
                editor.putString(REFRESH_TOKEN_KEY, refreshToken)
            }
            editor.putBoolean(AUTH_STATUS_KEY, true)
            editor.apply()
        } catch (e: Exception) {
            android.util.Log.e("TokenManager", "Failed to save token with refresh", e)
        }
    }

    /**
     * Sets the authentication status
     */
    fun setAuthStatus(isAuthenticated: Boolean) {
        try {
            sharedPreferences.edit()
                .putBoolean(AUTH_STATUS_KEY, isAuthenticated)
                .apply()
        } catch (e: Exception) {
            android.util.Log.e("TokenManager", "Failed to set auth status", e)
        }
    }
}