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
                "(function() { return window.puter && window.puter.auth ? window.puter.auth.isSignedIn() : false; })();"
            ) { result ->
                isSignedIn = result.removeSurrounding("\"").toBoolean()
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
                "    }" +
                "    return false;" +
                "  } catch (e) {" +
                "    console.error('Sign-in error:', e);" +
                "    return false;" +
                "  }" +
                "})();"
            ) { result ->
                isSignedIn = result.removeSurrounding("\"").toBoolean()
                latch.countDown()
            }
        }
        
        latch.await(5, TimeUnit.SECONDS)
        return isSignedIn
    }
}