package com.yourcompany.myagenticbrowser.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yourcompany.myagenticbrowser.R
import com.yourcompany.myagenticbrowser.agent.AiAgent
import com.yourcompany.myagenticbrowser.agent.AgentService
import com.yourcompany.myagenticbrowser.ai.puter.PuterClient
import com.yourcompany.myagenticbrowser.ai.puter.PuterConfigManager
import com.yourcompany.myagenticbrowser.browser.BrowserActivity
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.*

/**
 * Bottom sheet fragment for the swipable chat popup as described in the UI specification
 * This implements the top-left wireframe showing a chat interface that appears on swiping up
 */
class ChatBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var puterClient: PuterClient
    private lateinit var configManager: PuterConfigManager
    private lateinit var chatMessagesContainer: LinearLayout
    private lateinit var userMessageTextView: TextView
    private lateinit var aiTitleTextView: TextView
    private lateinit var aiSubtitleTextView: TextView
    private lateinit var aiResponseTextView: TextView
    private lateinit var followUpInput: EditText
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_popup_layout, container, false)

        // Initialize Puter.js components
        configManager = PuterConfigManager.getInstance(requireContext())
        puterClient = PuterClient()

        // Set up UI elements
        setupUI(view)

        Logger.logInfo("ChatBottomSheetFragment", "Chat bottom sheet fragment created through Puter.js infrastructure. All AI capabilities route through Puter.js as required.")

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            // Customize the dialog if needed
        }
    }

    private fun setupUI(view: View) {
        // Find UI elements
        chatMessagesContainer = view.findViewById(R.id.chatMessagesContainer)
        userMessageTextView = view.findViewById(R.id.userMessage)
        aiTitleTextView = view.findViewById(R.id.aiTitle)
        aiSubtitleTextView = view.findViewById(R.id.aiSubtitle)
        aiResponseTextView = view.findViewById(R.id.aiResponse)
        followUpInput = view.findViewById(R.id.followUpInput)

        // Remove the original example messages since we'll add our own
        chatMessagesContainer.removeAllViews()

        // Set up button listeners
        val plusButton = view.findViewById<ImageButton>(R.id.plusButton)
        val settingsButton = view.findViewById<ImageButton>(R.id.settingsButton)
        val sendButton = view.findViewById<ImageButton>(R.id.sendButton)

        plusButton.setOnClickListener {
            // Handle media/attachment upload
            handleAttachmentUpload()
        }

        settingsButton.setOnClickListener {
            // Handle settings (AI model selector, web search toggle, etc.)
            handleSettings()
        }

        sendButton.setOnClickListener {
            val message = followUpInput.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                followUpInput.setText("")
            }
        }

        // Make the input field respond to enter key as well
        followUpInput.setOnEditorActionListener { _, _, _ ->
            val message = followUpInput.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                followUpInput.setText("")
            }
            true
        }
    }

    private fun handleAttachmentUpload() {
        Toast.makeText(context, "Attachment upload functionality would be implemented here", Toast.LENGTH_SHORT).show()
        // In a real implementation, this would open a file picker or camera
    }

    private fun handleSettings() {
        // In a real implementation, this would open settings for AI model selection, web search toggle, etc.
        Toast.makeText(context, "Settings functionality would be implemented here", Toast.LENGTH_SHORT).show()
    }

    private fun sendMessage(message: String) {
        scope.launch {
            try {
                // Add user message to the chat
                addUserMessage(message)

                // Get AI response through Puter.js infrastructure
                val aiResponse = getAiResponse(message)

                // Add AI response to the chat
                addAiResponse(aiResponse)

                Logger.logInfo("ChatBottomSheetFragment", "Message sent through Puter.js infrastructure: $message")
            } catch (e: Exception) {
                Logger.logError("ChatBottomSheetFragment", "Error sending message through Puter.js infrastructure: ${e.message}", e)
                addErrorMessage("Error: ${e.message}")
            }
        }
    }

    private suspend fun getAiResponse(message: String): String {
        // Try to get the current WebView from the parent activity
        val webView = try {
            val parentActivity = activity
            if (parentActivity is BrowserActivity) {
                parentActivity.getCurrentWebViewFragment()?.getWebView()
            } else {
                null
            }
        } catch (e: Exception) {
            Logger.logError("ChatBottomSheetFragment", "Error getting WebView: ${e.message}", e)
            null
        }
        
        return if (webView != null) {
            puterClient.chat(
                webView = webView,
                message = message,
                context = "Current context for chat popup"
            )
        } else {
            "Unable to get response - no WebView available"
        }
    }

    private fun addUserMessage(message: String) {
        val userMessageView = TextView(context).apply {
            text = "USER <- $message"
            setBackgroundResource(R.drawable.user_message_background)
            setPadding(16, 12, 16, 12)
            setTextColor(resources.getColor(android.R.color.black, context?.theme))
            textSize = 14f
        }
        
        chatMessagesContainer.addView(userMessageView)
        
        // Scroll to bottom
        chatMessagesContainer.post {
            chatMessagesContainer.scrollTo(0, chatMessagesContainer.height)
        }
    }

    private fun addAiResponse(response: String) {
        // Create AI response title
        val aiTitleView = TextView(context).apply {
            text = "AI Response"
            setBackgroundResource(R.drawable.ai_message_background)
            setPadding(16, 12, 16, 4)
            setTextColor(resources.getColor(android.R.color.black, context?.theme))
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
        }
        
        // Create AI response subtitle
        val aiSubtitleView = TextView(context).apply {
            text = "Processing your request..."
            setBackgroundResource(R.drawable.ai_message_background)
            setPadding(16, 4, 16, 8)
            setTextColor(resources.getColor(android.R.color.darker_gray, context?.theme))
            textSize = 12f
        }
        
        // Create AI response content
        val aiResponseView = TextView(context).apply {
            text = response
            setBackgroundResource(R.drawable.ai_message_background)
            setPadding(16, 8, 16, 12)
            setTextColor(resources.getColor(android.R.color.black, context?.theme))
            textSize = 14f
        }
        
        chatMessagesContainer.addView(aiTitleView)
        chatMessagesContainer.addView(aiSubtitleView)
        chatMessagesContainer.addView(aiResponseView)
        
        // Scroll to bottom
        chatMessagesContainer.post {
            chatMessagesContainer.scrollTo(0, chatMessagesContainer.height)
        }
    }

    private fun addErrorMessage(error: String) {
        val errorView = TextView(context).apply {
            text = "Error: $error"
            setBackgroundResource(R.drawable.error_message_background)
            setPadding(16, 12, 16, 12)
            setTextColor(resources.getColor(android.R.color.holo_red_dark, context?.theme))
            textSize = 14f
        }
        
        chatMessagesContainer.addView(errorView)
        
        // Scroll to bottom
        chatMessagesContainer.post {
            chatMessagesContainer.scrollTo(0, chatMessagesContainer.height)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
        Logger.logInfo("ChatBottomSheetFragment", "Chat bottom sheet fragment destroyed. All AI capabilities through Puter.js infrastructure have been shut down.")
    }
}