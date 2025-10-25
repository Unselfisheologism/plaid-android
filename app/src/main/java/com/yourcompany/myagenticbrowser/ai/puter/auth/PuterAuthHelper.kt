package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.Intent

class PuterAuthHelper(private val context: Context) {
    private val tokenManager = TokenManager(context)

    fun isAuthenticated(): Boolean {
        return tokenManager.hasValidToken()
    }

    fun getAuthToken(): String? {
        return tokenManager.getValidToken()
    }
    
    fun clearAuthentication() {
        tokenManager.clearTokens()
    }

    fun launchAuthentication() {
        // Start the PuterAuthActivity for authentication
        val intent = Intent(context, PuterAuthActivity::class.java)
        if (context !is android.app.Activity) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
    
    companion object {
        const val AUTH_STATUS_CHANGED = "com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED"
    }
}