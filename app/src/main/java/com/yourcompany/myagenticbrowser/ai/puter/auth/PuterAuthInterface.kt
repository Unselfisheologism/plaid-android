// FILE: app/src/main/java/com/yourcompany/myagenticbrowser/ai/puter/auth/PuterAuthInterface.kt
package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import kotlinx.coroutines.*

class PuterAuthInterface(
    private val context: Context,
    private val webView: WebView,
    private val onAuthSuccess: () -> Unit,
    private val onAuthError: (String) -> Unit
) {
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var isAuthenticating = false

    @JavascriptInterface
    fun handlePuterLoaded() {
        // Puter.js is loaded, now we can attempt authentication
        authenticate()
    }

    @JavascriptInterface
    fun handleToken(token: String) {
        if (token.isNotEmpty()) {
            val tokenManager = TokenManager(context)
            tokenManager.saveToken(token)
            isAuthenticating = false
            onAuthSuccess()
        }
    }

    @JavascriptInterface
    fun handleAuthError(error: String) {
        isAuthenticating = false
        onAuthError(error)
    }

    fun authenticate() {
        if (isAuthenticating) return
        isAuthenticating = true
        
        scope.launch {
            try {
                // First ensure Puter.js is loaded
                val jsCheck = """
                    (function() {
                        return !!window.puter;
                    })();
                """.trimIndent()
                
                var isPuterLoaded = false
                withContext(Dispatchers.IO) {
                    // Evaluate JS synchronously on a background thread
                    val result = evaluateJavascriptSync(jsCheck)
                    isPuterLoaded = result?.trim()?.equals("true") ?: false
                }
                
                if (!isPuterLoaded) {
                    // Give Puter.js more time to load
                    delay(1000)
                    
                    withContext(Dispatchers.IO) {
                        val result = evaluateJavascriptSync(jsCheck)
                        isPuterLoaded = result?.trim()?.equals("true") ?: false
                    }
                }
                
                if (!isPuterLoaded) {
                    handleAuthError("Puter.js not loaded after timeout")
                    return@launch
                }
                
                // Now initiate authentication
                val jsCode = """
                    (async function() {
                        try {
                            if (window.puter && window.puter.auth) {
                                // This will open the authentication popup
                                const token = await window.puter.auth.signIn();
                                AndroidAuth.handleToken(token);
                            } else {
                                AndroidAuth.handleAuthError("Puter not initialized");
                            }
                        } catch (e) {
                            AndroidAuth.handleAuthError(e.message || "Unknown error");
                        }
                    })();
                """.trimIndent()
                
                webView.evaluateJavascript(jsCode, null)
                
            } catch (e: Exception) {
                handleAuthError("Authentication error: ${e.message}")
            }
        }
    }
    
    // Helper method to evaluate JS synchronously
    private fun evaluateJavascriptSync(script: String): String? {
        var result: String? = null
        val latch = java.util.concurrent.CountDownLatch(1)
        
        webView.evaluateJavascript(script) { value ->
            result = value
            latch.countDown()
        }
        
        latch.await(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
        return result
    }
}