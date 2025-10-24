package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import android.view.View
import android.widget.ProgressBar
import com.yourcompany.myagenticbrowser.R

class AuthActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth) // Added layout for progress indicator
        
        progressBar = findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
        
        handleRedirect(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleRedirect(intent)
    }

    // In AuthActivity.kt, replace the current handleRedirect with:
    private fun handleRedirect(intent: Intent?) {
        val authService = AuthService(this)
        val success = intent?.let { authService.handleAuthenticationCallback(it) } ?: false
    
        if (success) {
            Toast.makeText(this, "Authentication successful!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun exchangeCodeForToken(code: String) {
        val json = """{
            "code": "$code",
            "client_id": "myagenticbrowser",
            "redirect_uri": "myagenticbrowser://auth",
            "grant_type": "authorization_code"
        }""".trimIndent()
        
        val requestBody = json.toRequestBody("application/json".toMediaType())
        
        val request = Request.Builder()
            .url("https://puter.com/api/token")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    showError("Network error: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    
                    if (response.isSuccessful) {
                        try {
                            val responseBody = response.body?.string() ?: throw Exception("Empty response")
                            val json = JSONObject(responseBody)
                            
                            val token = json.getString("access_token")
                            val expiresIn = json.optLong("expires_in", 3600) // Default to 1 hour if not provided
                            
                            saveToken(token, expiresIn)
                            showSuccess()
                            
                            // Notify other components
                            sendAuthStatusBroadcast(true)
                        } catch (e: Exception) {
                            showError("Error processing response: ${e.message}")
                        }
                    } else {
                        try {
                            val errorBody = response.body?.string()
                            val errorMessage = if (!errorBody.isNullOrEmpty()) {
                                try {
                                    val json = JSONObject(errorBody)
                                    "${json.optString("error", "Authentication failed")}: ${json.optString("error_description", "")}"
                                } catch (e: Exception) {
                                    "Server error: ${response.code}"
                                }
                            } else {
                                "Authentication failed: ${response.code}"
                            }
                            showError(errorMessage)
                        } catch (e: Exception) {
                            showError("Authentication failed with status ${response.code}")
                        }
                    }
                }
            }
        })
    }

    private fun saveToken(token: String, expiresIn: Long) {
        val prefs = getSharedPreferences("puter_auth_prefs", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("puter_auth_token", token)
        editor.putLong("token_expiration", System.currentTimeMillis() + (expiresIn * 1000))
        editor.apply()
    }

    private fun sendAuthStatusBroadcast(isAuthenticated: Boolean) {
        val intent = Intent("com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED")
        intent.putExtra("is_authenticated", isAuthenticated)
        sendBroadcast(intent)
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        sendAuthStatusBroadcast(false)
        finish()
    }

    private fun showSuccess() {
        Toast.makeText(this, "Authentication successful!", Toast.LENGTH_SHORT).show()
    }
}