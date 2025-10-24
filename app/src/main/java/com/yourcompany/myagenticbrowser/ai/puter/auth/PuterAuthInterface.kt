package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
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
            // Enable popup windows for Puter.js authentication
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
            webView.settings.setSupportMultipleWindows(true)
            
            // Set up WebViewClient to handle authentication in a new tab instead of popup
            val activity = webView.context as? com.yourcompany.myagenticbrowser.browser.BrowserActivity
            if (activity != null) {
                // Use the same CustomWebChromeClient from PuterClient for consistency
                webView.webChromeClient = com.yourcompany.myagenticbrowser.ai.puter.PuterClient.CustomWebChromeClient(webView)
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