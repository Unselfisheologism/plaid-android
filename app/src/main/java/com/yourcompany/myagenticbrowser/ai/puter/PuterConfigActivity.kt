package com.yourcompany.myagenticbrowser.ai.puter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Activity for configuring Puter.js integration
 * This activity allows users to set up their Puter.js credentials
 * All AI capabilities route through Puter.js as required
 * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
 * Puter.js handles all AI provider endpoints and authentication internally as required
 */
class PuterConfigActivity : AppCompatActivity() {
    private lateinit var apiKeyEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puter_config)

        apiKeyEditText = findViewById(R.id.apiKeyEditText)
        saveButton = findViewById(R.id.saveButton)

        // Load existing API key if available
        val configManager = PuterConfigManager.getInstance(this)
        configManager.apiKey?.let { apiKeyEditText.setText(it) }

        saveButton.setOnClickListener {
            val apiKey = apiKeyEditText.text.toString().trim()
            if (apiKey.isNotEmpty()) {
                configManager.apiKey = apiKey
                configManager.isConfigured = true
                Toast.makeText(this, "Configuration saved", Toast.LENGTH_SHORT).show()
                Logger.logInfo("PuterConfigActivity", "Puter.js configuration saved. All AI capabilities will now route through Puter.js infrastructure as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
                finish()
            } else {
                Toast.makeText(this, "Please enter a valid API key", Toast.LENGTH_SHORT).show()
            }
        }
    }
}