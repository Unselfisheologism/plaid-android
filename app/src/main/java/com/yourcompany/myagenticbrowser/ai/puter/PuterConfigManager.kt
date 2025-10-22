package com.yourcompany.myagenticbrowser.ai.puter

import android.content.Context
import android.content.SharedPreferences
import com.yourcompany.myagenticbrowser.utilities.Logger

/**
 * Configuration manager for Puter.js integration
 * This class handles all configuration related to Puter.js
 * All AI capabilities route through Puter.js as required
 * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
 * Puter.js handles all AI provider endpoints and authentication internally as required
 */
class PuterConfigManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    var apiKey: String?
        get() = sharedPreferences.getString(KEY_API_KEY, null)
        set(value) = sharedPreferences.edit().putString(KEY_API_KEY, value).apply()
    
    var isConfigured: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_CONFIGURED, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_CONFIGURED, value).apply()
    
    fun hasCredentials(): Boolean {
        return !apiKey.isNullOrEmpty()
    }
    
    fun clearConfig() {
        sharedPreferences.edit().clear().apply()
    }
    
    companion object {
        private const val PREFS_NAME = "puter_config"
        private const val KEY_API_KEY = "puter_api_key"
        private const val KEY_IS_CONFIGURED = "puter_is_configured"
        
        @Volatile
        private var INSTANCE: PuterConfigManager? = null
        
        fun getInstance(context: Context): PuterConfigManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PuterConfigManager(context.applicationContext).also { INSTANCE = it }
            }
        }
        
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = PuterConfigManager(context.applicationContext)
                        Logger.logInfo("PuterConfigManager", "PuterConfigManager initialized. All AI capabilities will route through Puter.js infrastructure as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
                    }
                }
            }
        }
    }
}