package com.yourcompany.myagenticbrowser.browser

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.webkit.WebChromeClient
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.appbar.MaterialToolbar
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.browser.tab.TabManager
import com.yourcompany.myagenticbrowser.utilities.Logger
import com.yourcompany.myagenticbrowser.utilities.MemoryManager
import com.yourcompany.myagenticbrowser.agent.AiAgent
import com.yourcompany.myagenticbrowser.agent.AgentService
import com.yourcompany.myagenticbrowser.agent.SearchVisualizationActivity
import com.yourcompany.myagenticbrowser.ui.ChatBottomSheetFragment
import com.yourcompany.myagenticbrowser.ai.puter.auth.PuterAuthHelper

class BrowserActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabAdapter: TabAdapter
    private lateinit var tabManager: TabManager
    private lateinit var toolbar: MaterialToolbar
    private lateinit var puterAuthHelper: PuterAuthHelper
    
    private val authStatusReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED") {
                val isAuthenticated = intent.getBooleanExtra("is_authenticated", false)
                updateAuthStatus(isAuthenticated)
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        Logger.logInfo("BrowserActivity", "Creating BrowserActivity")

        // Initialize managers
        
        // Initialize tab management
        tabManager = TabManager(this)
        tabAdapter = TabAdapter(this, tabManager)
        
        // Initialize Puter authentication helper
        puterAuthHelper = PuterAuthHelper(this)
        
        viewPager = findViewById(R.id.viewPager)
        toolbar = findViewById(R.id.toolbar)
        
        viewPager.adapter = tabAdapter
        viewPager.offscreenPageLimit = 2 // Keep 3 tabs active maximum

        // Set up toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "MyAgenticBrowser - Powered by Puter.js"

        // Add new tab button
        findViewById<FloatingActionButton>(R.id.newTabButton).setOnClickListener {
            addNewTab()
        }
        
        // Add chat button
        findViewById<FloatingActionButton>(R.id.chatButton).setOnClickListener {
            showChatPopup()
        }
        
        // Add tabs icon click listener
        findViewById<ImageView>(R.id.tabsIcon).setOnClickListener {
            val intent = Intent(this, com.yourcompany.myagenticbrowser.ui.TabPreviewActivity::class.java)
            startActivity(intent)
        }
        
        // Add left bottom-arrow icon click listener (shows menu, back, forward, refresh options)
        findViewById<ImageView>(R.id.leftBottomArrowIcon).setOnClickListener {
            showLeftMenu()
        }
        
        // Add right bottom-arrow icon click listener (shows dropdown of 8 ultra-fake dummy buttons)
        findViewById<ImageView>(R.id.rightBottomArrowIcon).setOnClickListener {
            showRightMenu()
        }
        
        // Add wrench icon click listener (settings)
        findViewById<ImageView>(R.id.wrenchIcon).setOnClickListener {
            // Show settings
            showSettings()
        }

        // Add initial tab if none exist
        if (tabManager.getTabCount() == 0) {
            addAgentTab() // Use AI agent as default homepage per desire.md
        }
        tabAdapter.notifyDataSetChanged()
        
        // Register the authentication status receiver
        val filter = IntentFilter("com.yourcompany.myagenticbrowser.AUTH_STATUS_CHANGED")
        registerReceiver(authStatusReceiver, filter)
        
        MemoryManager.logMemoryUsage()
    }

    fun addNewTab(url: String = "https://www.google.com", owner: com.yourcompany.myagenticbrowser.browser.tab.TabOwner = com.yourcompany.myagenticbrowser.browser.tab.TabOwner.USER) {
        tabManager.addTab(url, owner)
        tabAdapter.notifyItemInserted(tabManager.getTabCount() - 1)
        
        // Switch to the new tab
        viewPager.currentItem = tabManager.getTabCount() - 1
        
        // Update toolbar title to reflect current tab
        updateToolbarTitle()
        
        Logger.logInfo("BrowserActivity", "Added new tab: $url, Owner: $owner, Total tabs: ${tabManager.getTabCount()}")
    }
    
    /**
     * Add a new AI agent tab as the default homepage
     */
    private fun addAgentTab() {
        // Load the agent homepage as a new tab and make it the default
        val agentUrl = "file:///android_asset/agent_home.html"
        tabManager.addTab(agentUrl, com.yourcompany.myagenticbrowser.browser.tab.TabOwner.USER)
        tabAdapter.notifyItemInserted(tabManager.getTabCount() - 1)
        
        // Switch to the new tab (making it the default)
        viewPager.currentItem = tabManager.getTabCount() - 1
        
        // Update toolbar title to reflect current tab
        updateToolbarTitle()
        
        Logger.logInfo("BrowserActivity", "Added AI agent tab as homepage: $agentUrl, Owner: USER, Total tabs: ${tabManager.getTabCount()}")
    }

    fun updateToolbarTitle() {
        if (viewPager.currentItem < tabManager.getTabCount()) {
            val currentTab = tabManager.getTabAt(viewPager.currentItem)
            supportActionBar?.title = if (currentTab.title.isNotBlank()) {
                currentTab.title
            } else {
                "Tab ${viewPager.currentItem + 1}"
            }
        }
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentByTag("f" + viewPager.currentItem)
        if (currentFragment is WebViewFragment) {
            if (currentFragment.canGoBack()) {
                currentFragment.goBack()
            } else {
                // If we can't go back in the current tab, remove the tab if it's not the last one
                if (tabManager.getTabCount() > 1) {
                    val currentIndex = viewPager.currentItem
                    tabManager.removeTabAt(currentIndex)
                    tabAdapter.notifyItemRemoved(currentIndex)
                    
                    // Adjust the current item if needed
                    if (currentIndex >= tabManager.getTabCount() && tabManager.getTabCount() > 0) {
                        viewPager.currentItem = tabManager.getTabCount() - 1
                    }
                    
                    // Update the adapter to reflect the removal
                    tabAdapter.notifyDataSetChanged()
                    
                    // Update toolbar title
                    updateToolbarTitle()
                    
                    Logger.logInfo("BrowserActivity", "Removed tab at position: $currentIndex, Total tabs: ${tabManager.getTabCount()}")
                } else {
                    super.onBackPressed()
                }
            }
        } else {
            super.onBackPressed()
        }
    }
    
    override fun onDestroy() {
        Logger.logInfo("BrowserActivity", "Destroying BrowserActivity")
        super.onDestroy()
        MemoryManager.gc()
    }
    
    override fun onDestroy() {
        try {
            unregisterReceiver(authStatusReceiver)
        } catch (e: IllegalArgumentException) {
            // Receiver was not registered, ignore
        }
        super.onDestroy()
    }
    
    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.browser_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_menu -> {
                showLeftMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    /**
     * Show the left menu with browser options like back, forward, refresh, etc.
     */
    private fun showLeftMenu() {
        // Get the current WebView fragment to access browser functionality
        val webViewFragment = getCurrentWebViewFragment()
        if (webViewFragment != null) {
            // Show a popup menu with browser actions
            val popup = PopupMenu(this, findViewById(R.id.leftBottomArrowIcon))
            
            // Add browser-specific actions
            popup.menu.add("Back").setOnMenuItemClickListener {
                webViewFragment.goBack()
                true
            }
            
            popup.menu.add("Forward").setOnMenuItemClickListener {
                if (webViewFragment.canGoForward()) {
                    webViewFragment.goForward()
                }
                true
            }
            
            popup.menu.add("Refresh").setOnMenuItemClickListener {
                webViewFragment.reload()
                true
            }
            
            popup.menu.add("Home").setOnMenuItemClickListener {
                // Navigate to the AI agent homepage
                webViewFragment.getWebView().loadUrl("file:///android_asset/agent_home.html")
                true
            }
            
            popup.show()
        } else {
            // If no WebView fragment is available, show a simple menu
            val popup = PopupMenu(this, findViewById(R.id.leftBottomArrowIcon))
            popup.menu.add("Refresh").setOnMenuItemClickListener {
                // Refresh current tab
                val currentWebViewFragment = getCurrentWebViewFragment()
                currentWebViewFragment?.reload()
                true
            }
            popup.show()
        }
    }
    
    /**
     * Show the right menu with proper side menu functionality
     */
    private fun showRightMenu() {
        // Show the proper side menu instead of dummy buttons
        showSideMenu()
    }
    
    
    /**
     * Get the current active WebView if available
     */
    fun getCurrentWebViewFragment(): WebViewFragment? {
        val currentFragment = supportFragmentManager.findFragmentByTag("f" + viewPager.currentItem)
        return if (currentFragment is WebViewFragment) {
            currentFragment
        } else {
            null
        }
    }
    
    /**
     * Update the AI agent context with current tab information
     */
    fun updateAgentContext() {
        val webViewFragment = getCurrentWebViewFragment()
        if (webViewFragment != null) {
            val webView = webViewFragment.getWebView()
            // Update the AI agent with the new context
            AgentService.updateContext(this, webView)
        }
    }
    
    /**
     * Show the chat popup as a bottom sheet
     */
    fun showChatPopup() {
        // Check if user is authenticated with Puter.js before showing chat popup
        if (puterAuthHelper.isAuthenticated()) {
            val chatBottomSheet = ChatBottomSheetFragment()
            chatBottomSheet.show(supportFragmentManager, "ChatBottomSheet")
        } else {
            // Show error message if not authenticated and prompt for authentication
            android.widget.Toast.makeText(
                this,
                "Please authenticate with Puter.js to use AI features. Starting authentication...",
                android.widget.Toast.LENGTH_LONG
            ).show()
            puterAuthHelper.launchAuthentication()
        }
    }
    
    /**
     * Show the chat popup with a pre-injected prompt and WebView context
     */
    fun showChatPopupWithPreInjectedPrompt(prompt: String, webView: android.webkit.WebView?) {
        // Check if user is authenticated with Puter.js before showing chat popup
        if (puterAuthHelper.isAuthenticated()) {
            val chatBottomSheet = ChatBottomSheetFragment()
            // Pass the prompt and WebView context to the chat fragment
            val bundle = Bundle()
            bundle.putString("preInjectedPrompt", prompt)
            // Pass WebView context - but WebView is not serializable, so we pass the URL instead
            bundle.putString("webViewContextUrl", webView?.url)
            chatBottomSheet.arguments = bundle
            chatBottomSheet.show(supportFragmentManager, "ChatBottomSheet")
        } else {
            // Show error message if not authenticated and prompt for authentication
            android.widget.Toast.makeText(
                this,
                "Please authenticate with Puter.js to use AI features. Starting authentication...",
                android.widget.Toast.LENGTH_LONG
            ).show()
            puterAuthHelper.launchAuthentication()
        }
        
        /**
         * Update authentication status and notify all tabs
         * Called when authentication status changes
         */
        fun updateAuthStatus(isAuthenticated: Boolean) {
            // Update the authentication status in shared preferences
            val prefs = getSharedPreferences("puter_auth_prefs", MODE_PRIVATE)
            prefs.edit()
                .putBoolean("is_authenticated", isAuthenticated)
                .apply()
                
            // Notify all WebView fragments about the authentication status change
            for (i in 0 until tabManager.getTabCount()) {
                val fragment = supportFragmentManager.findFragmentByTag("f$i")
                if (fragment is WebViewFragment) {
                    val webView = fragment.getWebView()
                    webView.post {
                        webView.evaluateJavascript(
                            "if (typeof updateAuthStatus === 'function') updateAuthStatus($isAuthenticated);",
                            null
                        )
                    }
                }
            }
        }
    }
    
    /**
     * Show settings - opens the side menu instead of a separate settings menu
     */
    private fun showSettings() {
        showSideMenu()
    }
    
    /**
     * Show the side menu as a bottom sheet fragment
     */
    private fun showSideMenu() {
        val sideMenuFragment = com.yourcompany.myagenticbrowser.ui.SideMenuFragment()
        sideMenuFragment.show(supportFragmentManager, "SideMenuBottomSheet")
    }
    
}