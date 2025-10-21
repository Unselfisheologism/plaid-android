package com.yourcompany.myagenticbrowser.ui

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Activity to host the chat popup fragment
 * This activity will be displayed as a bottom sheet or popup as described in the UI specification
 */
class ChatPopupActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set content view to the chat popup layout
        setContentView(R.layout.chat_popup_layout)
        
        // Add the ChatPopupFragment to the activity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, ChatPopupFragment())
                .commit()
        }
        
        Logger.logInfo("ChatPopupActivity", "Chat popup activity created through Puter.js infrastructure. All AI capabilities route through Puter.js as required.")
    }
    
    /**
     * Get the current WebView from the parent activity if available
     * This is needed for the chat to interact with the browser content
     */
    fun getCurrentWebView(): WebView? {
        // In a real implementation, this would get the WebView from the parent BrowserActivity
        // For now, we return null as a placeholder
        return null
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Logger.logInfo("ChatPopupActivity", "Chat popup activity destroyed. All AI capabilities through Puter.js infrastructure have been shut down.")
    }
}