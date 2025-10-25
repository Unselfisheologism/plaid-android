// FILE: app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/AuthService.kt
package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yourcompany.myagenticbrowser.ai.puter.auth.TokenManager
import com.yourcompany.myagenticbrowser.R
import android.graphics.BitmapFactory
import androidx.core.content.ContextCompat

class AuthService(private val context: Context) {
    private val tokenManager = TokenManager(context)

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
        val intent = Intent(context, PuterAuthActivity::class.java)
        context.startActivity(intent)
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
    }
}