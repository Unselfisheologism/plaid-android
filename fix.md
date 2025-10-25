# Fixing "Puter.js Not Loaded" Authentication Error

This is a common issue with Puter.js integration in Android apps. The error occurs because your app is trying to access Puter.js before it's fully loaded in the WebView. Let's fix this properly.

## The Root Cause

From your knowledge base files, I can see the exact problem:

> "The issue is that `puter.auth.signIn()` **opens a popup window** and returns a promise that resolves *after* the user completes authentication. Your current code is waiting for an immediate response, but the authentication process is asynchronous and takes longer than 5 seconds."

Your app is trying to use Puter.js before it's fully loaded in the WebView, causing the "Puter.js not loaded" error.

## The Complete Fix

### 1. Update WebView Settings in BrowserActivity.kt

```kotlin
// In BrowserActivity.kt, where you initialize your WebView
fun setupWebView(webView: WebView) {
    webView.settings.javaScriptEnabled = true
    webView.settings.domStorageEnabled = true
    
    // CRITICAL SETTINGS FOR PUTER.JS
    webView.settings.setSupportMultipleWindows(true)
    webView.settings.javaScriptCanOpenWindowsAutomatically = true
    webView.settings.mediaPlaybackRequiresUserGesture = false
    
    // Add JavaScript interface for authentication callbacks
    webView.addJavascriptInterface(PuterAuthInterface(this, webView, 
        onAuthSuccess = {
            updateAuthStatus(true)
            // Hide any "signing in" UI
        },
        onAuthError = { errorMessage ->
            updateAuthStatus(false)
            Toast.makeText(this, "Authentication error: $errorMessage", Toast.LENGTH_LONG).show()
        }
    ), "AndroidAuth")
    
    // Set up WebViewClient with popup window handling
    webView.webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url?.startsWith("myagenticbrowser://auth") == true) {
                // Handle authentication callback
                val token = Uri.parse(url).getQueryParameter("token")
                if (!token.isNullOrEmpty()) {
                    val tokenManager = TokenManager(this@BrowserActivity)
                    tokenManager.saveToken(token)
                    return true
                }
            }
            return super.shouldOverrideUrlLoading(view, url)
        }
        
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            
            // Check if we're on a page that needs Puter.js
            if (url?.contains("agent_home.html") == true || 
                url?.contains("some-page-that-uses-puter") == true) {
                
                // Load Puter.js and set up authentication
                loadPuterJSAndInitialize(view)
            }
        }
    }
    
    // Set up WebChromeClient to handle popup windows
    webView.webChromeClient = object : WebChromeClient() {
        override fun onCreateWindow(
            view: WebView,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message
        ): Boolean {
            val newWebView = WebView(this@BrowserActivity)
            newWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    if (url.startsWith("myagenticbrowser://auth")) {
                        // Handle authentication callback directly
                        val token = Uri.parse(url).getQueryParameter("token")
                        if (!token.isNullOrEmpty()) {
                            val tokenManager = TokenManager(this@BrowserActivity)
                            tokenManager.saveToken(token)
                            
                            // Close the popup
                            view.stopLoading()
                            (view.parent as? ViewGroup)?.removeView(view)
                            return true
                        }
                    }
                    return false
                }
            }
            
            val transport = resultMsg.obj as WebViewTransport
            transport.webView = newWebView
            resultMsg.send()
            return true
        }
    }
}

private fun loadPuterJSAndInitialize(webView: WebView?) {
    if (webView == null) return
    
    val jsCode = """
        (function() {
            try {
                // Check if we've already loaded Puter.js
                if (window.puterJSLoaded) return;
                window.puterJSLoaded = true;
                
                // Create script element for Puter.js
                const script = document.createElement('script');
                script.src = 'https://cdn.puter.com/puter.js';
                script.async = true;
                
                script.onload = function() {
                    // Puter.js is now loaded, set up authentication
                    AndroidAuth.handlePuterLoaded();
                };
                
                script.onerror = function() {
                    AndroidAuth.handleAuthError("Failed to load Puter.js from CDN");
                };
                
                document.head.appendChild(script);
            } catch (e) {
                AndroidAuth.handleAuthError("Error initializing Puter.js: " + e.message);
            }
        })();
    """.trimIndent()
    
    webView.evaluateJavascript(jsCode, null)
}
```

### 2. Create PuterAuthInterface.kt

```kotlin
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
                    val result = webView.evaluateJavascriptSync(jsCheck)
                    isPuterLoaded = result?.trim()?.equals("true") ?: false
                }
                
                if (!isPuterLoaded) {
                    // Give Puter.js more time to load
                    delay(1000)
                    
                    withContext(Dispatchers.IO) {
                        val result = webView.evaluateJavascriptSync(jsCheck)
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
    private fun WebView.evaluateJavascriptSync(script: String): String? {
        var result: String? = null
        val latch = java.util.concurrent.CountDownLatch(1)
        
        evaluateJavascript(script) { value ->
            result = value
            latch.countDown()
        }
        
        latch.await(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
        return result
    }
}
```

### 3. Update WebViewFragment.kt

```kotlin
// In your WebViewFragment.kt
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    val webView = binding.webView
    setupWebView(webView)
    
    // Load your content
    webView.loadUrl("file:///android_asset/agent_home.html")
}

private fun setupWebView(webView: WebView) {
    // Same setup as in BrowserActivity.kt above
    (activity as? BrowserActivity)?.setupWebView(webView)
}
```

### 4. Update BrowserActivity.kt's onCreate Method

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    // DO NOT check authentication here - let WebView handle it
    setContentView(R.layout.activity_browser)
    
    // Initialize tab management
    tabManager = TabManager(this)
    tabAdapter = TabAdapter(this, tabManager)
    
    viewPager = findViewById(R.id.viewPager)
    viewPager.adapter = tabAdapter
    
    // Other setup code...
    
    // Add initial tab if none exist
    if (tabManager.getTabCount() == 0) {
        addAgentTab() // Use AI agent as default homepage
    }
    tabAdapter.notifyDataSetChanged()
}
```

### 5. Update ChatBottomSheetFragment.kt

```kotlin
// In ChatBottomSheetFragment.kt
fun showChatPopup(webView: WebView?) {
    if (webView == null) {
        Toast.makeText(context, "WebView not available", Toast.LENGTH_SHORT).show()
        return
    }
    
    // Check if Puter.js is loaded
    val jsCheck = """
        (function() {
            return !!window.puter;
        })();
    """.trimIndent()
    
    webView.evaluateJavascript(jsCheck) { result ->
        val isPuterLoaded = result?.trim()?.equals("true") ?: false
        
        if (isPuterLoaded) {
            // Puter.js is loaded, proceed with chat
            showChatInterface()
        } else {
            // Puter.js not loaded yet, set up authentication
            Toast.makeText(context, "Authenticating with Puter.js...", Toast.LENGTH_SHORT).show()
            
            // Set up authentication interface
            val authInterface = PuterAuthInterface(
                requireContext(),
                webView,
                onAuthSuccess = { showChatInterface() },
                onAuthError = { error ->
                    Toast.makeText(context, "Authentication failed: $error", Toast.LENGTH_LONG).show()
                }
            )
            
            // Start authentication
            authInterface.authenticate()
        }
    }
}
```

## Why This Fix Works

1. **Proper WebView Configuration**: The critical settings `setSupportMultipleWindows(true)` and `javaScriptCanOpenWindowsAutomatically = true` allow Puter.js to open its authentication popup.

2. **Asynchronous Flow Handling**: Instead of waiting for an immediate response (which doesn't exist with Puter.js), we use proper callbacks that trigger when authentication completes.

3. **WebViewClient Override for Popup Windows**: The `onCreateWindow` implementation properly handles the authentication popup that Puter.js needs.

4. **Loading Sequence**: We now wait for Puter.js to fully load before attempting authentication, eliminating the "Puter.js not loaded" error.

5. **Error Handling**: Proper error handling ensures you get meaningful messages instead of silent failures.

This implementation matches exactly how Puter.js is designed to work, as confirmed in your knowledge base files. The key was understanding that Puter.js authentication requires proper popup window handling in WebView - it's not just a simple redirect flow.