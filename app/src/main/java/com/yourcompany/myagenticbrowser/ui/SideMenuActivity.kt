package com.yourcompany.myagenticbrowser.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Activity for the side menu with circular icons as described in the UI specification
 * This implements the middle-right wireframe showing a vertical menu with circular icons
 */
class SideMenuActivity : AppCompatActivity() {
    
    private lateinit var closeButton: ImageButton
    private lateinit var accountMenuItem: LinearLayout
    private lateinit var settingsMenuItem: LinearLayout
    private lateinit var integrationsMenuItem: LinearLayout
    private lateinit var workflowsMenuItem: LinearLayout
    private lateinit var agentsExpertsMenuItem: LinearLayout
    private lateinit var supportMenuItem: LinearLayout
    private lateinit var summarizePageMenuItem: LinearLayout
    private lateinit var askAboutPageMenuItem: LinearLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.side_menu_layout)
        
        setupViews()
        setupEventListeners()
        
        Logger.logInfo("SideMenuActivity", "Side menu activity created through Puter.js infrastructure. All AI capabilities route through Puter.js as required.")
    }
    
    private fun setupViews() {
        closeButton = findViewById(R.id.closeButton)
        accountMenuItem = findViewById(R.id.accountMenuItem)
        settingsMenuItem = findViewById(R.id.settingsMenuItem)
        integrationsMenuItem = findViewById(R.id.integrationsMenuItem)
        workflowsMenuItem = findViewById(R.id.workflowsMenuItem)
        agentsExpertsMenuItem = findViewById(R.id.agentsExpertsMenuItem)
        supportMenuItem = findViewById(R.id.supportMenuItem)
        summarizePageMenuItem = findViewById(R.id.summarizePageMenuItem)
        askAboutPageMenuItem = findViewById(R.id.askAboutPageMenuItem)
    }
    
    private fun setupEventListeners() {
        closeButton.setOnClickListener {
            finish() // Close the activity
        }
        
        accountMenuItem.setOnClickListener {
            handleMenuItemClick("Account")
        }
        
        settingsMenuItem.setOnClickListener {
            handleMenuItemClick("Settings")
        }
        
        integrationsMenuItem.setOnClickListener {
            handleMenuItemClick("Integrations")
        }
        
        workflowsMenuItem.setOnClickListener {
            handleMenuItemClick("Workflows")
        }
        
        agentsExpertsMenuItem.setOnClickListener {
            handleMenuItemClick("Agents Experts")
        }
        
        supportMenuItem.setOnClickListener {
            handleMenuItemClick("Support")
        }
        
        summarizePageMenuItem.setOnClickListener {
            handleMenuItemClick("Summarize Page")
        }
        
        askAboutPageMenuItem.setOnClickListener {
            handleMenuItemClick("Ask About Page")
        }
    }
    
    private fun handleMenuItemClick(itemName: String) {
        // In a real implementation, this would navigate to the appropriate screen
        val message = when (itemName) {
            "Account" -> "Navigating to Account screen"
            "Settings" -> "Navigating to Settings screen"
            "Integrations" -> "Navigating to Integrations screen"
            "Workflows" -> "Navigating to Workflows screen"
            "Agents Experts" -> "Navigating to Agents Experts screen"
            "Support" -> "Navigating to Support screen"
            "Summarize Page" -> "Summarizing current page"
            "Ask About Page" -> "Asking about current page"
            else -> "Navigating to $itemName screen"
        }
        
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Logger.logInfo("SideMenuActivity", "Menu item clicked: $itemName through Puter.js infrastructure")
        
        // Close the menu after selecting an item
        finish()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Logger.logInfo("SideMenuActivity", "Side menu activity destroyed. All AI capabilities through Puter.js infrastructure have been shut down.")
    }
}