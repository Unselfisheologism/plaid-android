// FILE: app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthService.kt
package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import android.net.Uri
import com.yourcompany.myagenticbrowser.ai.puter.auth.TokenManager

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
            val code = data.getQueryParameter("code")
            val error = data.getQueryParameter("error")
            
            if (!code.isNullOrEmpty()) {
                // The code should be exchanged for a token in the AuthActivity
                // This is handled in AuthActivity, not here
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