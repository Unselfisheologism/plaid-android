package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import com.yourcompany.myagenticbrowser.utilities.Logger

class PuterAuthInterface(
    private val context: Context,
    private val webView: WebView,
    private val onAuthSuccess: (() -> Unit)? = null,
    private val onAuthError: ((String) -> Unit)? = null
) {
    @JavascriptInterface
    fun isUserSignedIn(callback: String) {
        webView.post {
            val jsCode = """
                (function() {
                    const signedIn = window.puter && window.puter.auth ? window.puter.auth.isSignedIn() : false;
                    $callback(signedIn);
                    return signedIn;
                })();
            """.trimIndent()
            
            webView.evaluateJavascript(jsCode, null)
        }
    }

    @JavascriptInterface
    fun signInUser() {
        Logger.logInfo("PuterAuthInterface", "Sign in requested")
        webView.post {
            // Set up WebViewClient to handle authentication in a new tab instead of popup
            val activity = webView.context as? com.yourcompany.myagenticbrowser.browser.BrowserActivity
            if (activity != null) {
                // Override the window creation to open in a new tab instead of a popup
                webView.webChromeClient = object : android.webkit.WebChromeClient() {
                    override fun onCreateWindow(
                        view: android.webkit.WebView,
                        isDialog: Boolean,
                        isUserGesture: Boolean,
                        resultMsg: android.os.Message
                    ): Boolean {
                        // Instead of creating a popup, open in a new tab
                        // We'll get the URL that would be loaded in the popup
                        val transport = resultMsg.obj as android.webkit.WebView.WebViewTransport
                        val newWebView = android.webkit.WebView(activity)
                        
                        // Set up the new WebView to detect when the authentication page loads
                        newWebView.webViewClient = object : android.webkit.WebViewClient() {
                            override fun onPageStarted(view: android.webkit.WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                if (url?.contains("puter.com/auth") == true) {
                                    // This is an authentication page, so we'll open it in a new tab
                                    activity.runOnUiThread {
                                        // Create a new tab with this URL
                                        activity.addNewTab(url, com.yourcompany.myagenticbrowser.browser.tab.TabOwner.USER)
                                    }
                                    
                                    // Close the temporary WebView and send the result
                                    view?.destroy()
                                    resultMsg.sendToTarget()
                                }
                            }
                        }
                        
                        newWebView.loadUrl("about:blank") // Load a blank page to trigger the client
                        return true
                    }
                }
            }
            
            // Inject JavaScript to handle authentication
            val jsCode = """
                (async function() {
                    try {
                        if (window.puter && window.puter.auth) {
                            await window.puter.auth.signIn();
                            const isSignedIn = window.puter.auth.isSignedIn();
                            const userInfo = isSignedIn ? await window.puter.auth.getUser() : null;
                            AndroidInterface.handleAuthResult(isSignedIn, JSON.stringify(userInfo), null);
                        } else {
                            AndroidInterface.handleAuthResult(false, null, "Puter not initialized");
                        }
                    } catch (e) {
                        console.error('Sign-in error:', e);
                        AndroidInterface.handleAuthResult(false, null, e.message);
                    }
                })();
            """.trimIndent()
            
            webView.evaluateJavascript(jsCode, null)
        }
    }
    
    @JavascriptInterface
    fun signOutUser() {
        Logger.logInfo("PuterAuthInterface", "Sign out requested")
        webView.post {
            val jsCode = """
                (async function() {
                    try {
                        if (window.puter && window.puter.auth) {
                            await window.puter.auth.signOut();
                            AndroidInterface.handleAuthResult(false, null, "Signed out successfully");
                        } else {
                            AndroidInterface.handleAuthResult(false, null, "Puter not initialized");
                        }
                    } catch (e) {
                        console.error('Sign-out error:', e);
                        AndroidInterface.handleAuthResult(false, null, e.message);
                    }
                })();
            """.trimIndent()
            
            webView.evaluateJavascript(jsCode, null)
        }
    }
    
    @JavascriptInterface
    fun initializePuterJS(callback: String) {
        webView.post {
            val jsCode = """
                (function() {
                    if (typeof puter === 'undefined') {
                        const script = document.createElement('script');
                        script.src = 'https://js.puter.com/v2/';
                        script.onload = function() {
                            window.puterJSLoaded = true;
                            $callback(true);
                        };
                        script.onerror = function() {
                            window.puterJSLoaded = false;
                            $callback(false);
                        };
                        document.head.appendChild(script);
                        return true;
                    } else {
                        $callback(true);
                        return true;
                    }
                })();
            """.trimIndent()
            
            webView.evaluateJavascript(jsCode, null)
        }
    }
    
    @JavascriptInterface
    fun handleAuthResult(success: Boolean, userInfo: String?, errorMessage: String?) {
        if (success) {
            Logger.logInfo("PuterAuthInterface", "Authentication successful")
            Toast.makeText(context, "Successfully authenticated with Puter!", Toast.LENGTH_SHORT).show()
            onAuthSuccess?.invoke()
        } else {
            val error = errorMessage ?: "Authentication failed"
            Logger.logInfo("PuterAuthInterface", "Authentication failed: $error")
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            onAuthError?.invoke(error)
        }
    }
}