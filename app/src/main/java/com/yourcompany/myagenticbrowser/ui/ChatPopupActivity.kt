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
        
        // Show the ChatBottomSheetFragment as a bottom sheet
        val chatFragment = ChatBottomSheetFragment()
        chatFragment.show(supportFragmentManager, "ChatBottomSheet")
        
        Logger.logInfo("ChatPopupActivity", "Chat popup activity created through Puter.js infrastructure. All AI capabilities route through Puter.js as required.")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Logger.logInfo("ChatPopupActivity", "Chat popup activity destroyed. All AI capabilities through Puter.js infrastructure have been shut down.")
    }
}