package com.yourcompany.myagenticbrowser.ui

import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.browser.BrowserActivity
import com.yourcompany.myagenticbrowser.browser.tab.TabManager
import com.yourcompany.myagenticbrowser.browser.tab.TabOwner
import com.yourcompany.myagenticbrowser.browser.tab.TabStatus
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Activity to show tab previews in a grid layout as mentioned in ui-description.md
 * This shows the bottom-right wireframe with 6 tabs in square shapes arranged in two columns
 */
class TabPreviewActivity : AppCompatActivity() {
    private lateinit var tabManager: TabManager
    private lateinit var gridView: GridView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tab_preview_activity)
        
        // Get reference to the tab manager from the BrowserActivity
        val browserActivity = parent as? BrowserActivity
        tabManager = browserActivity?.tabManager ?: run {
            // If we can't get the parent activity, finish this activity
            finish()
            return
        }
        
        gridView = findViewById(R.id.gridView)
        
        // Set up the grid adapter
        val adapter = TabPreviewAdapter(this, tabManager)
        gridView.adapter = adapter
        
        // Set up close button
        findViewById<ImageView>(R.id.closeButton).setOnClickListener {
            finish()
        }
        
        Logger.logInfo("TabPreviewActivity", "Tab preview activity created showing ${tabManager.getTabCount()} tabs")
    }
}

/**
 * Adapter for the tab preview grid
 */
class TabPreviewAdapter(
    private val context: TabPreviewActivity,
    private val tabManager: TabManager
) : android.widget.BaseAdapter() {
    
    override fun getCount(): Int = tabManager.getTabCount()
    
    override fun getItem(position: Int): Any = tabManager.getTabAt(position)
    
    override fun getItemId(position: Int): Long = position.toLong()
    
    override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup?): android.view.View {
        val view = convertView ?: android.view.LayoutInflater.from(context)
            .inflate(R.layout.tab_indicator_layout, parent, false)
        
        val tab = tabManager.getTabAt(position)
        val tabTitle = view.findViewById<TextView>(R.id.tabTitle)
        val tabPreview = view.findViewById<ImageView>(R.id.tabPreview)
        val agentIndicator = view.findViewById<ImageView>(R.id.agentIndicator)
        val statusIndicator = view.findViewById<ImageView>(R.id.statusIndicator)
        
        // Set tab title
        tabTitle.text = tab.title
        
        // For now, set a placeholder image as preview
        // In a real implementation, this would show a thumbnail of the webpage
        tabPreview.setImageResource(android.R.drawable.ic_menu_recent_history)
        
        // Set agent indicator based on tab owner
        when (tab.owner) {
            TabOwner.USER -> {
                agentIndicator.setImageResource(android.R.drawable.ic_menu_my_calendar) // User icon
            }
            TabOwner.AGENT -> {
                agentIndicator.setImageResource(android.R.drawable.ic_menu_myplaces) // Agent icon
            }
        }
        
        // Set status indicator based on tab status
        when (tab.status) {
            TabStatus.ACTIVE -> {
                statusIndicator.setImageResource(android.R.drawable.presence_online) // Green for active
                statusIndicator.setColorFilter(android.graphics.Color.GREEN)
            }
            TabStatus.DORMANT -> {
                statusIndicator.setImageResource(android.R.drawable.presence_invisible) // Yellow for dormant
                statusIndicator.setColorFilter(android.graphics.Color.YELLOW)
            }
        }
        
        // Add click listener to switch to this tab
        view.setOnClickListener {
            // In a real implementation, this would switch to the selected tab in the main activity
            context.finish() // For now, just close the preview
        }
        
        return view
    }
}