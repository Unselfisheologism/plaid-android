// FILE: app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthService.kt
package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import android.net.Uri

class AuthService(private val context: Context) {
    private val tokenManager = TokenManager(context)
    private val authHelper = PuterAuthHelper(context)

    companion object {
        const val AUTH_SCHEME = "myagenticbrowser"
        const val AUTH_HOST = "auth"
    }

    /**
     * Checks if the user is currently authenticated
     */
    fun isAuthenticated(): Boolean {
        return tokenManager.hasValidToken()
    }

    /**
     * Initiates the authentication flow by opening the browser
     */
    fun startAuthentication() {
        authHelper.launchAuthentication()
    }

    /**
     * Handles the authentication callback from the browser redirect
     */
    fun handleAuthenticationCallback(intent: Intent): Boolean {
        val data = intent.data ?: return false
        
        if (data.scheme == AUTH_SCHEME && data.host == AUTH_HOST) {
            val token = data.getQueryParameter("token")
            val error = data.getQueryParameter("error")
            
            if (!token.isNullOrEmpty()) {
                tokenManager.saveToken(token)
                return true
            } else if (!error.isNullOrEmpty()) {
                // Handle error
                return false
            }
        }
        return false
    }

    /**
     * Gets the current authentication token
     */
    fun getAuthToken(): String? {
        return tokenManager.getValidToken()
    }

    /**
     * Logs out the current user
     */
    fun logout() {
        tokenManager.clearTokens()
        authHelper.clearAuthentication()
    }
}