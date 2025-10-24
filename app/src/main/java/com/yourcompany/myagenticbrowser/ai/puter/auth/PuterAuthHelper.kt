package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.yourcompany.myagenticbrowser.R

class PuterAuthHelper(private val context: Context) {
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("puter_auth_prefs", Context.MODE_PRIVATE)

    fun isAuthenticated(): Boolean {
        val token = sharedPreferences.getString("puter_auth_token", null)
        return !token.isNullOrEmpty()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString("puter_auth_token", null)
    }

    fun launchAuthentication() {
        val clientId = "myagenticbrowser"
        val redirectUri = "myagenticbrowser://auth"
        val authUrl = "https://puter.com/api/auth?" +
                "client_id=$clientId&" +
                "redirect_uri=${Uri.encode(redirectUri)}&" +
                "response_type=code"

        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(context.getColor(R.color.purple_500)) // Using app's primary color
            .build()
        
        customTabsIntent.launchUrl(context, Uri.parse(authUrl))
    }

    fun clearAuthentication() {
        sharedPreferences.edit()
            .remove("puter_auth_token")
            .apply()
    }
}