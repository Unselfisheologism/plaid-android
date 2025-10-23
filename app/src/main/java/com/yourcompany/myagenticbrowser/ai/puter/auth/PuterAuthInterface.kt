package com.yourcompany.myagenticbrowser.ai.puter.auth

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class PuterAuthInterface(
    private val context: Context,
    private val webView: WebView
) {
    @JavascriptInterface
    fun isUserSignedIn(): Boolean {
        var isSignedIn = false
        val latch = CountDownLatch(1)
        
        webView.post {
            webView.evaluateJavascript(
                "(function() { " +
                "  if (window.puter && window.puter.auth) {" +
                "    return window.puter.auth.isSignedIn();" +
                "  } else {" +
                "    console.log('Puter.js not loaded yet'); return false;" +
                "  }" +
                "})();"
            ) { result ->
                try {
                    isSignedIn = result.removeSurrounding("\"").toBoolean()
                } catch (e: Exception) {
                    isSignedIn = false
                }
                latch.countDown()
            }
        }
        
        latch.await(2, TimeUnit.SECONDS)
        return isSignedIn
    }

    @JavascriptInterface
    fun signInUser(): Boolean {
        var isSignedIn = false
        val latch = CountDownLatch(1)
        
        webView.post {
            webView.evaluateJavascript(
                "(async function() { " +
                "  try {" +
                "    if (window.puter && window.puter.auth) {" +
                "      await window.puter.auth.signIn();" +
                "      return await window.puter.auth.isSignedIn();" +
                "    } else {" +
                "      console.error('Puter.js not available'); return false;" +
                "    }" +
                "  } catch (e) {" +
                "    console.error('Sign-in error:', e);" +
                "    return false;" +
                "  }" +
                "})();"
            ) { result ->
                try {
                    isSignedIn = result.removeSurrounding("\"").toBoolean()
                } catch (e: Exception) {
                    isSignedIn = false
                }
                latch.countDown()
            }
        }
        
        latch.await(5, TimeUnit.SECONDS)
        return isSignedIn
    }
    
    @JavascriptInterface
    fun signOutUser(): Boolean {
        var isSignedOut = false
        val latch = CountDownLatch(1)
        
        webView.post {
            webView.evaluateJavascript(
                "(async function() { " +
                " try {" +
                "    if (window.puter && window.puter.auth) {" +
                "      await window.puter.auth.signOut();" +
                "      return !(await window.puter.auth.isSignedIn());" +
                "    } else {" +
                "      console.error('Puter.js not available'); return false;" +
                "    }" +
                "  } catch (e) {" +
                "    console.error('Sign-out error:', e);" +
                "    return false;" +
                "  }" +
                "})();"
            ) { result ->
                try {
                    isSignedOut = result.removeSurrounding("\"").toBoolean()
                } catch (e: Exception) {
                    isSignedOut = false
                }
                latch.countDown()
            }
        }
        
        latch.await(5, TimeUnit.SECONDS)
        return isSignedOut
    }
    
    @JavascriptInterface
    fun initializePuterJS(): Boolean {
        var success = false
        val latch = CountDownLatch(1)
        
        webView.post {
            webView.evaluateJavascript(
                "(function() { " +
                "  if (typeof puter === 'undefined') { " +
                "    const script = document.createElement('script');" +
                "    script.src = 'https://js.puter.com/v2/';" +
                "    script.onload = function() { window.puterJSLoaded = true; };" +
                "    script.onerror = function() { window.puterJSLoaded = false; };" +
                "    document.head.appendChild(script);" +
                "    return true;" +
                "  } else { " +
                "    return true;" +
                "  }" +
                "})();"
            ) { result ->
                try {
                    success = result.removeSurrounding("\"").toBoolean()
                } catch (e: Exception) {
                    success = false
                }
                latch.countDown()
            }
        }
        
        latch.await(3, TimeUnit.SECONDS)
        return success
    }
}