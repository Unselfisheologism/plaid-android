package com.yourcompany.myagenticbrowser.browser

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.browser.cookies.CookieManager
import com.yourcompany.myagenticbrowser.agent.AiAgent
import com.yourcompany.myagenticbrowser.agent.AgentService
import com.yourcompany.myagenticbrowser.utilities.Logger
import com.yourcompany.myagenticbrowser.utilities.MemoryManager
import com.yourcompany.myagenticbrowser.browser.PuterJSInterface
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.ai.puter.auth.PuterAuthInterface
import org.json.JSONObject

/**
 * Fragment for displaying WebView content through Puter.js infrastructure
 * Handles WebView lifecycle and interaction with the AI agent through Puter.js
 * All AI capabilities route through Puter.js as required
 */
class WebViewFragment : Fragment() {
    private lateinit var webView: WebView
    private var currentUrl: String = "https://www.google.com"
    private var position: Int = -1
    private lateinit var puterClient: PuterClient
    private lateinit var puterJSInterface: PuterJSInterface
    
    companion object {
        private const val ARG_URL = "url"
        private const val ARG_POSITION = "position"
        
        fun newInstance(url: String, position: Int = -1): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString(ARG_URL, url)
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate()
        arguments?.let {
            currentUrl = it.getString(ARG_URL) ?: "https://www.google.com"
            position = it.getInt(ARG_POSITION, -1)
        }
        Logger.logInfo("WebViewFragment", "WebViewFragment created with Puter.js integration. All AI capabilities will route through Puter.js infrastructure as required.")
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_webview, container, false)
        
        webView = view.findViewById(R.id.webView)
        setupWebView()
        
        webView.loadUrl(currentUrl)
        
        Logger.logInfo("WebViewFragment", "WebView created for URL: $currentUrl at position: $position. Puter.js integration enabled. All AI capabilities will route through Puter.js infrastructure as required.")
        
        return view
    }
    
    /**
     * Set up WebView with Puter.js integration through Puter.js infrastructure
     * All AI capabilities route through Puter.js as required
     */
    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.setAppCacheEnabled(true)
        webView.settings.databaseEnabled = true
        webView.settings.setGeolocationEnabled(false)
        webView.settings.setSaveFormData(false)
        
        // Initialize Puter.js client and interface for communication with JavaScript through Puter.js infrastructure
        puterClient = PuterClient()
        puterJSInterface = PuterJSInterface(puterClient, webView)
        webView.addJavascriptInterface(
            puterJSInterface,
            "Android"
        )
        
        // Add the authentication interface
        webView.addJavascriptInterface(
            PuterAuthInterface(requireContext(), webView),
            "puterAuth"
        )
        
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                currentUrl = url ?: currentUrl
                
                Logger.logInfo("WebViewFragment", "Page finished loading: $currentUrl through Puter.js infrastructure")
                
                // Load Puter.js script into the page through Puter.js infrastructure
                view?.evaluateJavascript(puterClient.getPuterJSScript(), null)
                
                // Update the tab title in the TabManager if we have a position
                if (position >= 0) {
                    val activity = activity as? BrowserActivity
                    activity?.let { browserActivity ->
                        browserActivity.runOnUiThread {
                            browserActivity.tabManager.updateTabTitle(position, view?.title ?: "New Tab")
                            browserActivity.updateToolbarTitle()
                        }
                    }
                }
                
                // Extract page content for agent context through Puter.js infrastructure
                view?.evaluateJavascript(
                    "(function() { " +
                    "  return JSON.stringify({" +
                    "    title: document.title," +
                    "    url: window.location.href," +
                    "    content: document.body.innerText" +
                    " });" +
                    "})()",
                    { result ->
                        try {
                            // Remove the quotes and escape characters that WebView adds
                            var cleanedResult = result
                            if (cleanedResult.startsWith("\"") && cleanedResult.endsWith("\"")) {
                                cleanedResult = cleanedResult.substring(1, cleanedResult.length - 1)
                                    .replace("\\\"", "\"")
                                    .replace("\\\\", "\\")
                            }
                            
                            // Parse the JSON to extract title, url, and content
                            val jsonObject = org.json.JSONObject(cleanedResult)
                            val extractedUrl = jsonObject.optString("url", url ?: "")
                            val extractedTitle = jsonObject.optString("title", view?.title ?: "")
                            val extractedContent = jsonObject.optString("content", "")
                            
                            val context = AiAgent.AgentContext(
                                activeTabUrl = extractedUrl,
                                activeTabTitle = extractedTitle,
                                cookies = CookieManager.getAllCookies(),
                                pageContent = extractedContent,
                                webView = webView
                            )
                            AgentService.updateContext(requireContext(), context)
                        } catch (e: Exception) {
                            Logger.logError("WebViewFragment", "Error extracting page context through Puter.js: ${e.message}")
                            
                            // Fallback: create context with basic info
                            val context = AiAgent.AgentContext(
                                activeTabUrl = url ?: "",
                                activeTabTitle = view?.title ?: "",
                                cookies = CookieManager.getAllCookies(),
                                pageContent = "",
                                webView = webView
                            )
                            AgentService.updateContext(requireContext(), context)
                        }
                    }
                )
            }
            
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                Logger.logError("WebViewFragment", "WebView error through Puter.js: ${error?.description} for URL: ${request?.url}")
            }
        }
        
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                // Update progress if needed
                Logger.logInfo("WebViewFragment", "Loading progress through Puter.js: $newProgress%")
            }
            
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                Logger.logInfo("WebViewFragment", "Received title through Puter.js: $title")
                
                // Update the tab title in the TabManager if we have a position
                if (position >= 0) {
                    val activity = activity as? BrowserActivity
                    activity?.let { browserActivity ->
                        browserActivity.runOnUiThread {
                            browserActivity.tabManager.updateTabTitle(position, title ?: "New Tab")
                            browserActivity.updateToolbarTitle()
                        }
                    }
                }
            }
        }
    }
    
    fun canGoBack(): Boolean = webView.canGoBack()
    
    fun goBack() = webView.goBack()
    
    fun getUrl(): String = currentUrl
    
    fun reload() = webView.reload()
    
    fun canGoForward(): Boolean = webView.canGoForward()
    
    fun goForward() = webView.goForward()
    
    /**
     * Get the WebView instance
     */
    fun getWebView(): WebView = webView
    
    override fun onDestroy() {
        Logger.logInfo("WebViewFragment", "Destroying WebView for URL: $currentUrl. Puter.js integration disabled.")
        try {
            webView.stopLoading()
            webView.clearHistory()
            webView.clearCache(true)
            webView.loadUrl("about:blank")
            webView.onPause()
            webView.removeAllViews()
            webView.destroy()
        } catch (e: Exception) {
            Logger.logError("WebViewFragment", "Error destroying WebView with Puter.js integration: ${e.message}")
        }
        super.onDestroy()
        
        MemoryManager.gc()
    }
}