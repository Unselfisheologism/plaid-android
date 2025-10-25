package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.view.View
import android.widget.ProgressBar
import com.yourcompany.myagenticbrowser.R

class AuthActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        
        progressBar = findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        
        handleAuthenticationResult(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleAuthenticationResult(intent)
    }

    private fun handleAuthenticationResult(intent: Intent?) {
        val data = intent?.data ?: run {
            showError("No data received from authentication")
            return
        }

        // Check if this is our authentication callback
        if (data.scheme == "myagenticbrowser" && data.host == "auth") {
            val token = data.getQueryParameter("token")
            val error = data.getQueryParameter("error")
            
            if (!token.isNullOrEmpty()) {
                // Puter.js returns the token directly - no exchange needed!
                saveToken(token)
                showSuccess()
                sendAuthStatusBroadcast(true)
            } else if (!error.isNullOrEmpty()) {
                val errorDescription = data.getQueryParameter("error_description")
                showError("Authentication error: $error${if (errorDescription != null) " - $errorDescription" else ""}")
            } else {
                showError("Invalid authentication response - no token received")
            }
        } else {
            // If not a callback, start authentication flow
            if (intent?.action == Intent.ACTION_VIEW) {
                showError("Invalid redirect URI")
            } else {
                startAuthentication()
            }
        }
        
        finish()
    }

    private fun startAuthentication() {
        val authUrl = "https://puter.com/action/sign-in"
        
        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(getColor(R.color.primary))
            .build()
            
        customTabsIntent.launchUrl(this, Uri.parse(authUrl))
    }

    private fun saveToken(token: String) {
        val tokenManager = TokenManager(this)
        tokenManager.saveToken(token)
    }

    private fun sendAuthStatusBroadcast(isAuthenticated: Boolean) {
        val intent = Intent("com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED")
        intent.putExtra("is_authenticated", isAuthenticated)
        sendBroadcast(intent)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        sendAuthStatusBroadcast(false)
    }

    private fun showSuccess() {
        Toast.makeText(this, "Authentication successful!", Toast.LENGTH_SHORT).show()
    }
}