package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import android.net.Uri
import okhttp3.*
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.Callback
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class AuthActivity : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleRedirect(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleRedirect(intent)
    }

    private fun handleRedirect(intent: Intent?) {
        val data = intent?.data ?: run {
            finish()
            return
        }

        if (data.scheme == "myagenticbrowser" && data.host == "auth") {
            val code = data.getQueryParameter("code")
            if (!code.isNullOrEmpty()) {
                exchangeCodeForToken(code)
            } else {
                val error = data.getQueryParameter("error")
                Toast.makeText(this, "Authentication failed: $error", Toast.LENGTH_LONG).show()
                finish()
            }
        } else {
            finish()
        }
    }

    private fun exchangeCodeForToken(code: String) {
        val formBody = FormBody.Builder()
            .add("code", code)
            .add("client_id", "myagenticbrowser")
            .add("redirect_uri", "myagenticbrowser://auth")
            .add("grant_type", "authorization_code")
            .build()

        val request = Request.Builder()
            .url("https://puter.com/api/token")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@AuthActivity, "Token exchange failed: ${e.message}", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        try {
                            val responseBody = response.body?.string()
                            val json = JSONObject(responseBody!!)
                            val token = json.getString("access_token")
                            saveToken(token)
                            Toast.makeText(this@AuthActivity, "Authentication successful!", Toast.LENGTH_SHORT).show()
                            
                            // Notify the BrowserActivity about the authentication status change
                            val intent = Intent("com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED")
                            intent.putExtra("is_authenticated", true)
                            sendBroadcast(intent)
                        } catch (e: Exception) {
                            Toast.makeText(this@AuthActivity, "Error parsing response: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@AuthActivity, "Token exchange failed: ${response.code}", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }
        })
    }

    private fun saveToken(token: String) {
        val prefs = getSharedPreferences("puter_auth_prefs", MODE_PRIVATE)
        prefs.edit()
            .putString("puter_auth_token", token)
            .apply()
    }

}