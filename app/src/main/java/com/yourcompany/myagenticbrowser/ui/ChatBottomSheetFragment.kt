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
import com.yourcompany.myagenticbrowser.browser.BrowserActivity
import com.yourcompany.myagenticbrowser.utilities.Logger
import kotlinx.coroutines.*

/**
 * Bottom sheet fragment for the swipable chat popup as described in the UI specification
 * This implements the top-left wireframe showing a chat interface that appears on swiping up
 */
class ChatBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var puterClient: PuterClient
    private lateinit var chatMessagesContainer: LinearLayout
    private lateinit var userMessageTextView: TextView
    private lateinit var aiTitleTextView: TextView
    private lateinit var aiSubtitleTextView: TextView
    private lateinit var aiResponseTextView: TextView
    private lateinit var followUpInput: EditText
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var preInjectedPrompt: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_popup_layout, container, false)

        // Initialize Puter.js components
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Check for pre-injected prompt
        preInjectedPrompt = arguments?.getString("preInjectedPrompt")
        if (preInjectedPrompt != null) {
            // Send the pre-injected prompt automatically after checking authentication
            scope.launch {
                // Give a small delay to ensure UI is ready
                kotlinx.coroutines.delay(500)
                
                // Check authentication before sending pre-injected prompt
                val authenticated = checkAuthentication()
                if (authenticated) {
                    sendMessage(preInjectedPrompt!!)
                } else {
                    addErrorMessage("Please authenticate with Puter.js to use the AI chat feature.")
                }
            }
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
                // Check authentication before sending message
                scope.launch {
                    val authenticated = checkAuthentication()
                    if (authenticated) {
                        sendMessage(message)
                    } else {
                        addErrorMessage("Authentication required. Please sign in with Puter.js to use the AI chat feature.")
                    }
                }
                followUpInput.setText("")
            }
        }

        // Make the input field respond to enter key as well
        followUpInput.setOnEditorActionListener { _, _, _ ->
            val message = followUpInput.text.toString().trim()
            if (message.isNotEmpty()) {
                scope.launch {
                    val authenticated = checkAuthentication()
                    if (authenticated) {
                        sendMessage(message)
                    } else {
                        addErrorMessage("Authentication required. Please sign in with Puter.js to use the AI chat feature.")
                    }
                }
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

    private suspend fun checkAuthentication(): Boolean = withContext(Dispatchers.Main) {
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
        
        if (webView != null) {
            // Check if user is authenticated with Puter.js
            puterClient.loadPuterJS(webView)
            // Give time for Puter.js to load
            delay(100)
            
            // First check if already signed in
            var isAuthenticated = false
            val latch = java.util.concurrent.CountDownLatch(1)
            
            webView.evaluateJavascript(
                "(function() { return window.puter && window.puter.auth ? window.puter.auth.isSignedIn() : false; })();"
            ) { result ->
                isAuthenticated = result.removeSurrounding("\"").toBoolean()
                latch.countDown()
            }
            
            try {
                latch.await(2, java.util.concurrent.TimeUnit.SECONDS)
            } catch (e: InterruptedException) {
                // Handle interruption
            }
            
            if (!isAuthenticated) {
                // Try to sign in
                val signInLatch = java.util.concurrent.CountDownLatch(1)
                webView.evaluateJavascript(
                    "(async function() { " +
                    "  try {" +
                    "    if (window.puter && window.puter.auth) {" +
                    "      await window.puter.auth.signIn();" +
                    "      return await window.puter.auth.isSignedIn();" +
                    "    }" +
                    "    return false;" +
                    " } catch (e) {" +
                    "    console.error('Sign-in error:', e);" +
                    "    return false;" +
                    " }" +
                    "})();"
                ) { result ->
                    isAuthenticated = result.removeSurrounding("\"").toBoolean()
                    signInLatch.countDown()
                }
                
                try {
                    signInLatch.await(5, java.util.concurrent.TimeUnit.SECONDS)
                } catch (e: InterruptedException) {
                    // Handle interruption
                }
            }
            
            isAuthenticated
        } else {
            false
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
            // Ensure Puter.js is loaded and authenticated before making chat request
            puterClient.loadPuterJS(webView)
            // Give time for Puter.js to load
            kotlinx.coroutines.delay(1000)
            try {
                puterClient.chat(
                    webView = webView,
                    message = message,
                    context = "Current context for chat popup"
                )
            } catch (e: Exception) {
                Logger.logError("ChatBottomSheetFragment", "Error in Puter.js chat: ${e.message}", e)
                if (e is SecurityException || e.message?.contains("Authentication") == true) {
                    "Authentication required. Please authenticate with Puter.js to access AI features."
                } else {
                    "Error: ${e.message}"
                }
            }
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