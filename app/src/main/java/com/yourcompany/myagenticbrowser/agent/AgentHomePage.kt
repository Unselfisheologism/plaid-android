package com.yourcompany.myagenticbrowser.agent

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.agent.voice.TTSManager
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.ai.puter.PuterConfigManager
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.*

/**
 * Agent home page activity with voice command support through Puter.js infrastructure
 * All AI capabilities route through Puter.js as required
 * No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
 * Puter.js handles all AI provider endpoints and authentication internally as required
 */
class AgentHomePage : AppCompatActivity() {
    private lateinit var puterClient: PuterClient
    private lateinit var configManager: PuterConfigManager
    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false
    
    private val agentResponseReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = intent?.getStringExtra("type") ?: ""
            val result = intent?.getStringExtra("result") ?: ""
            
            runOnUiThread {
                when (type) {
                    "success" -> {
                        updateAgentResponse(result)
                        TTSManager.speak(result)
                    }
                    "error" -> {
                        updateAgentResponse("Error: $result")
                        TTSManager.speak("Error: $result")
                    }
                    "automation" -> {
                        updateAgentResponse("Automation: $result")
                        TTSManager.speak("Performing automation: $result")
                    }
                    "search" -> {
                        updateAgentResponse("Search results: $result")
                        TTSManager.speak("Search results: $result")
                    }
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agent_home)
        
        // Initialize Puter.js components
        configManager = PuterConfigManager.getInstance(this)
        puterClient = PuterClient(configManager)
        
        // Initialize speech recognizer
        initializeSpeechRecognizer()
        
        // Set up UI elements
        setupUI()
        
        // Register receiver for agent responses
        registerReceiver(agentResponseReceiver, IntentFilter("AGENT_RESULT"))
        
        Logger.logInfo("AgentHomePage", "Agent home page created with voice command support through Puter.js infrastructure. All AI capabilities route through Puter.js as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used. Puter.js handles all AI provider endpoints and authentication internally.")
    }
    
    /**
     * Initialize speech recognizer for voice commands
     */
    private fun initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        }
    }
    
    /**
     * Set up UI elements and event listeners
     */
    private fun setupUI() {
        // Voice command button
        val voiceCommandButton = findViewById<Button>(R.id.voiceCommandButton)
        voiceCommandButton.setOnClickListener {
            startVoiceRecognition()
        }
        
        // Text command input
        val commandInput = findViewById<EditText>(R.id.commandInput)
        val sendButton = findViewById<Button>(R.id.sendButton)
        sendButton.setOnClickListener {
            val command = commandInput.text.toString().trim()
            if (command.isNotEmpty()) {
                processCommand(command)
                commandInput.setText("")
            }
        }
        
        // Settings button
        val settingsButton = findViewById<Button>(R.id.settingsButton)
        settingsButton.setOnClickListener {
            startActivity(Intent(this, com.yourcompany.myagenticbrowser.ai.puter.PuterConfigActivity::class.java))
        }
    }
    
    /**
     * Start voice recognition for commands
     */
    private fun startVoiceRecognition() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) 
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, 
                arrayOf(Manifest.permission.RECORD_AUDIO), 
                REQUEST_RECORD_AUDIO_PERMISSION)
            return
        }
        
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition not available", Toast.LENGTH_SHORT).show()
            return
        }
        
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, java.util.Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your command")
        }
        
        try {
            startActivityForResult(intent, REQUEST_VOICE_RECOGNITION)
            isListening = true
            updateListeningStatus(true)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No speech recognition activity found", Toast.LENGTH_SHORT).show()
        }
    }
    
    /**
     * Process a text or voice command through the agent service
     */
    private fun processCommand(command: String) {
        updateAgentResponse("Processing: $command...")
        
        // Send command to agent service
        val intent = Intent(this, AgentService::class.java)
        intent.putExtra("command", command)
        startService(intent)
        
        Logger.logInfo("AgentHomePage", "Sent command to agent service through Puter.js infrastructure: $command")
    }
    
    /**
     * Update the agent response display
     */
    private fun updateAgentResponse(response: String) {
        val responseTextView = findViewById<TextView>(R.id.responseTextView)
        responseTextView.text = response
    }
    
    /**
     * Update listening status UI
     */
    private fun updateListeningStatus(listening: Boolean) {
        val voiceCommandButton = findViewById<Button>(R.id.voiceCommandButton)
        voiceCommandButton.text = if (listening) "Listening..." else "Voice Command"
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == REQUEST_VOICE_RECOGNITION && resultCode == RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val command = results?.get(0) ?: ""
            
            if (command.isNotEmpty()) {
                processCommand(command)
            }
        }
        
        isListening = false
        updateListeningStatus(false)
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startVoiceRecognition()
            } else {
                Toast.makeText(this, "Microphone permission is required for voice commands", Toast.LENGTH_LONG).show()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(agentResponseReceiver)
        } catch (e: IllegalArgumentException) {
            // Receiver was not registered
        }
        
        speechRecognizer?.destroy()
        Logger.logInfo("AgentHomePage", "Agent home page destroyed. All AI capabilities through Puter.js infrastructure have been shut down. Puter.js handles all AI provider endpoints and authentication internally as required. No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used.")
    }
    
    companion object {
        private const val REQUEST_VOICE_RECOGNITION = 1001
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 1002
    }
}