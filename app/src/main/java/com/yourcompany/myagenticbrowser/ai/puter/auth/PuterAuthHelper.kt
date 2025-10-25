package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.yourcompany.myagenticbrowser.R

class PuterAuthHelper(private val context: Context) {
    private val tokenManager = TokenManager(context)

    fun isAuthenticated(): Boolean {
        return tokenManager.hasValidToken()
    }

    fun getAuthToken(): String? {
        return tokenManager.getValidToken()
    }

    fun launchAuthentication() {
        val redirectUri = "myagenticbrowser://auth"
        
        // CRITICAL FIX: Use the correct mobile authentication URL
        // From knowledge base: "The embedded_in_popup=true parameter is only for browser-based embeds and should be omitted for mobile apps"
        val authUrl = "https://puter.com/action/sign-in?" +
                "redirect_uri=${Uri.encode(redirectUri)}&" +
                "response_type=token" // Must use token response type, not code

        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(context.getColor(R.color.purple_500))
            .build()
        
        customTabsIntent.launchUrl(context, Uri.parse(authUrl))
    }
    
    fun clearAuthentication() {
        tokenManager.clearTokens()
    }
    
    companion object {
        const val AUTH_STATUS_CHANGED = "com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED"
    }
}