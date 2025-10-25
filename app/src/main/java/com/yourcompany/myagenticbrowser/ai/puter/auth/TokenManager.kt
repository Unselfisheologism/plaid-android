// FILE: app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/TokenManager.kt
package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.security.GeneralSecurityException

class TokenManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences by lazy {
        try {
            val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            EncryptedSharedPreferences.create(
                "secure_puter_auth_prefs",
                "prefs_key", // This is the second string parameter required by this signature
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
     * Saves the authentication token and its expiry time
     */
    fun saveToken(accessToken: String, expiresIn: Long = 3600) {
        val expiryTime = System.currentTimeMillis() + (expiresIn * 1000)
        
        try {
            sharedPreferences.edit()
                .putString(ACCESS_TOKEN_KEY, accessToken)
                .putLong(TOKEN_EXPIRY_KEY, expiryTime)
                .apply()
        } catch (e: Exception) {
            // If saving to encrypted preferences fails, log the error
            android.util.Log.e("TokenManager", "Failed to save token to encrypted preferences", e)
        }
    }

    /**
     * Saves the authentication token, refresh token and its expiry time
     */
    fun saveTokenWithRefresh(accessToken: String, refreshToken: String, expiresIn: Long = 3600) {
        val expiryTime = System.currentTimeMillis() + (expiresIn * 1000)
        
        try {
            sharedPreferences.edit()
                .putString(ACCESS_TOKEN_KEY, accessToken)
                .putString(REFRESH_TOKEN_KEY, refreshToken)
                .putLong(TOKEN_EXPIRY_KEY, expiryTime)
                .apply()
        } catch (e: Exception) {
            // If saving to encrypted preferences fails, log the error
            android.util.Log.e("TokenManager", "Failed to save token with refresh to encrypted preferences", e)
        }
    }

    /**
     * Gets the current access token if it's still valid
     */
    fun getValidToken(): String? {
        return try {
            val token = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
            val expiryTime = sharedPreferences.getLong(TOKEN_EXPIRY_KEY, 0)
            
            if (token != null && System.currentTimeMillis() < expiryTime) {
                token
            } else {
                null
            }
        } catch (e: Exception) {
            // If reading from encrypted preferences fails, log the error and return null
            android.util.Log.e("TokenManager", "Failed to read token from encrypted preferences", e)
            null
        }
    }

    /**
     * Checks if a valid token exists
     */
    fun hasValidToken(): Boolean {
        return try {
            getValidToken() != null
        } catch (e: Exception) {
            // If checking token validity fails, log the error and return false
            android.util.Log.e("TokenManager", "Failed to check token validity", e)
            false
        }
    }

    /**
     * Clears all authentication tokens
     */
    fun clearTokens() {
        try {
            sharedPreferences.edit()
                .remove(ACCESS_TOKEN_KEY)
                .remove(TOKEN_EXPIRY_KEY)
                .remove(REFRESH_TOKEN_KEY)
                .apply()
        } catch (e: Exception) {
            // If clearing tokens fails, log the error
            android.util.Log.e("TokenManager", "Failed to clear tokens from encrypted preferences", e)
        }
    }

    /**
     * Returns the SharedPreferences editor for direct access if needed
     */
    fun getPreferencesEditor() = try {
        sharedPreferences.edit()
    } catch (e: Exception) {
        // If getting editor fails, log the error and return null
        android.util.Log.e("TokenManager", "Failed to get editor from encrypted preferences", e)
        null
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
            // If setting auth status fails, log the error
            android.util.Log.e("TokenManager", "Failed to set auth status in encrypted preferences", e)
        }
    }
    
    /**
     * Gets the authentication status
     */
    fun getAuthStatus(): Boolean {
        return try {
            sharedPreferences.getBoolean(AUTH_STATUS_KEY, false)
        } catch (e: Exception) {
            // If getting auth status fails, log the error and return false
            android.util.Log.e("TokenManager", "Failed to get auth status from encrypted preferences", e)
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
            // If getting refresh token fails, log the error and return null
            android.util.Log.e("TokenManager", "Failed to get refresh token from encrypted preferences", e)
            null
        }
    }

    /**
     * Saves the refresh token
     */
    fun saveRefreshToken(refreshToken: String) {
        try {
            sharedPreferences.edit()
                .putString(REFRESH_TOKEN_KEY, refreshToken)
                .apply()
        } catch (e: Exception) {
            // If saving refresh token fails, log the error
            android.util.Log.e("TokenManager", "Failed to save refresh token to encrypted preferences", e)
        }
    }

    /**
     * Checks if token needs to be refreshed (will expire within the next 5 minutes)
     */
    fun needsTokenRefresh(): Boolean {
        return try {
            val expiration = sharedPreferences.getLong(TOKEN_EXPIRY_KEY, 0)
            expiration > 0 && System.currentTimeMillis() > (expiration - 300000)
        } catch (e: Exception) {
            // If checking token refresh fails, log the error and return false
            android.util.Log.e("TokenManager", "Failed to check if token needs refresh", e)
            false
        }
    }
}