package com.yourcompany.myagenticbrowser.agent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Search visualization activity for displaying search processes through Puter.js infrastructure
 * This activity shows the conversation between the main AI model and Perplexity Sonar model
 * All AI capabilities route through Puter.js as required
 * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
 * Puter.js handles all AI provider endpoints and authentication internally as required
 */
class SearchVisualizationActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_visualization)
        
        // Add the SearchVisualizationFragment to the activity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SearchVisualizationFragment())
                .commit()
        }
        
        Logger.logInfo("SearchVisualizationActivity", "Search visualization activity created through Puter.js infrastructure. All AI capabilities route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Logger.logInfo("SearchVisualizationActivity", "Search visualization activity destroyed. All AI capabilities through Puter.js infrastructure have been shut down. Puter.js handles all AI provider endpoints and authentication internally as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used.")
    }
}