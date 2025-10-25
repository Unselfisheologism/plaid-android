package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R

class PuterAuthActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private val tokenManager = TokenManager(this)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puter_auth)
        
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.mediaPlaybackRequiresUserGesture = false
        
        // Enable JavaScript interface
        webView.addJavascriptInterface(AuthJavaScriptInterface(), "AndroidAuth")
        
        // Load a minimal HTML page with Puter.js
        val html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Puter Authentication</title>
            </head>
            <body>
                <div style="display: flex; justify-content: center; align-items: center; height: 100vh;">
                    <div class="spinner"></div>
                    <p>Authenticating with Puter...<br>Please wait while Puter.js loads.</p>
                </div>
                <style>
                    .spinner {
                        width: 40px;
                        height: 40px;
                        border: 4px solid rgba(0, 0, 0, 0.1);
                        border-top: 4px solid #3498db;
                        border-radius: 50%;
                        animation: spin 1s linear infinite;
                        margin-right: 10px;
                    }
                    @keyframes spin {
                        0% { transform: rotate(0deg); }
                        100% { transform: rotate(360deg); }
                    }
                </style>
                <script>
                    function loadPuterJS() {
                        const script = document.createElement('script');
                        script.src = 'https://cdn.puter.com/puter.js';
                        script.async = true;
                        
                        script.onload = function() {
                            // Puter.js loaded successfully, now start authentication after a brief delay
                            setTimeout(startAuthentication, 1000);
                        };
                        
                        script.onerror = function() {
                            AndroidAuth.handleError("Failed to load Puter.js from CDN");
                        };
                        
                        document.head.appendChild(script);
                    }
                    
                    function startAuthentication() {
                        // Wait a bit more to ensure Puter.js is fully initialized
                        setTimeout(function() {
                            if (window.puter && window.puter.auth) {
                                puter.auth.signIn().then(token => {
                                    AndroidAuth.handleToken(token);
                                }).catch(error => {
                                    AndroidAuth.handleError(error.message || "Authentication failed");
                                });
                            } else {
                                AndroidAuth.handleError("Puter.js not loaded or not initialized");
                            }
                        }, 100);
                    }
                    
                    // Start loading Puter.js when page loads
                    window.onload = loadPuterJS;
                </script>
            </body>
            </html>
        """.trimIndent()
        
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
        
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // The JavaScript in the HTML will automatically trigger authentication
            }
        }
    }
    
    inner class AuthJavaScriptInterface {
        @android.webkit.JavascriptInterface
        fun handleToken(token: String) {
            // Save token and finish activity
            tokenManager.saveToken(token)
            sendAuthStatusBroadcast(true)
            finish()
        }
        
        @android.webkit.JavascriptInterface
        fun handleError(error: String) {
            Log.e("PuterAuth", "Authentication error: $error")
            Toast.makeText(this@PuterAuthActivity, "Authentication failed: $error", Toast.LENGTH_LONG).show()
            sendAuthStatusBroadcast(false)
            finish()
        }
    }
    
    private fun sendAuthStatusBroadcast(isAuthenticated: Boolean) {
        val intent = Intent("com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED")
        intent.putExtra("is_authenticated", isAuthenticated)
        sendBroadcast(intent)
    }
}