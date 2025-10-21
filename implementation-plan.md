# implementation-plan.md

# Agentic Browser Mobile Application - Implementation Plan

## 1. Project Structure

```
MyAgenticBrowser/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/yourcompany/myagenticbrowser/
│   │   │   │   ├── browser/
│   │   │   │   │   ├── BrowserActivity.kt
│   │   │   │   │   ├── TabAdapter.kt
│   │   │   │   │   ├── WebViewFragment.kt
│   │   │   │   │   ├── tab/
│   │   │   │   │   │   ├── TabManager.kt
│   │   │   │   │   │   ├── TabDiscardPolicy.kt
│   │   │   │   │   │   └── TabState.kt
│   │   │   │   │   └── cookies/
│   │   │   │   │       ├── CookieManager.kt
│   │   │   │   │       ├── CookieAccessService.kt
│   │   │   │   │       └── CookieDomainPolicy.kt
│   │   │   │   ├── agent/
│   │   │   │   │   ├── AiAgent.kt
│   │   │   │   │   ├── AgentService.kt
│   │   │   │   │   ├── AgentHomePage.kt
│   │   │   │   │   ├── voice/
│   │   │   │   │   │   ├── VoiceRecognizer.kt
│   │   │   │   │   │   └── TTSManager.kt
│   │   │   │   │   └── context/
│   │   │   │   │       ├── TabContextAnalyzer.kt
│   │   │   │   │       └── PageContentExtractor.kt
│   │   │   │   ├── automation/
│   │   │   │   │   ├── ScreenInteractionService.kt
│   │   │   │   │   ├── Finger.kt
│   │   │   │   │   ├── AccessibilityMonitor.kt
│   │   │   │   │   └── visual/
│   │   │   │   │       ├── OverlayManager.kt
│   │   │   │   │       └── HighlightView.kt
│   │   │   │   ├── workflow/
│   │   │   │   │   ├── WorkflowEngine.kt
│   │   │   │   │   ├── WorkflowBuilder.kt
│   │   │   │   │   ├── WorkflowStorage.kt
│   │   │   │   │   ├── nodes/
│   │   │   │   │   │   ├── BaseNode.kt
│   │   │   │   │   │   ├── GmailNode.kt
│   │   │   │   │   │   ├── NotionNode.kt
│   │   │   │   │   │   └── WebAutomationNode.kt
│   │   │   │   │   └── visual/
│   │   │   │   │       ├── NodeEditor.kt
│   │   │   │   │       └── WorkflowCanvas.kt
│   │   │   │   ├── ai/
│   │   │   │   │   ├── puter/
│   │   │   │   │   │   ├── PuterClient.kt
│   │   │   │   │   │   ├── PuterConfigManager.kt
│   │   │   │   │   │   ├── PuterSearchOrchestrator.kt
│   │   │   │   │   │   └── model/
│   │   │   │   │   │       ├── ChatModel.kt
│   │   │   │   │   │       └── SearchModel.kt
│   │   │   │   │   └── image/
│   │   │   │   │       └── ImageGenerator.kt
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── util/
│   │   │   │       ├── PermissionHelper.kt
│   │   │   │       └── MemoryManager.kt
│   │   │   └── res/
│   │   │       ├── layout/
│   │   │       ├── drawable/
│   │   │       ├── values/
│   │   │       └── xml/
│   │   └── AndroidManifest.xml
│   └── build.gradle
└── build.gradle
```

## 2. Implementation Phases

### Phase 1: Core Browser Foundation (5-7 days)

#### 1.1 Project Setup
- Create new Android Studio project with Kotlin
- Configure build.gradle with necessary dependencies
- Set up proper package structure as defined in project structure
- Configure AndroidManifest.xml with required permissions

#### 1.2 Basic WebView Container
- Implement BrowserActivity with tab management system using ViewPager2
- Configure the activity to handle tab creation, switching, and navigation
- Implement proper back button handling to navigate within the current tab
- Set up tab indicators for visual tab management

#### 1.3 Tab Management and Memory Optimization
- Implement TabAdapter with memory management capabilities
- Design tab state tracking system to monitor active/inactive tabs
- Implement tab discarding policy to maintain optimal memory usage
- Configure WebView lifecycle management to prevent memory leaks

### Phase 2: Puter.js AI Integration (6-8 days)

#### 2.1 Puter.js Client Infrastructure
- Implement PuterClient with secure communication capabilities
- Design request/response structures for AI interactions
- Configure authentication and error handling mechanisms
- Implement connection management for reliable API communication

#### 2.2 Puter Configuration Management
- Create secure storage system for API credentials
- Implement configuration loading and validation
- Design user interface for API key management
- Add encryption for sensitive configuration data

#### 2.3 Puter Search Orchestration System
- Develop natural language search query formulation system
- Implement communication flow between main chat model and Perplexity Sonar model
- Design result processing pipeline for search results
- Create source extraction mechanism for attribution

#### 2.4 AI Agent Integration with Puter.js
- Update AiAgent to route all AI interactions through Puter.js
- Implement search command handling using natural language search
- Design image generation command processing
- Create response handling system for different AI response types

### Phase 3: Cookie Management System (2-3 days)

#### 3.1 Cookie Access Infrastructure
- Implement CookieManager with domain-specific access capabilities
- Create session extraction methods for key services (Gmail, Notion)
- Design session validation system for active sessions
- Implement cookie retrieval system for multiple domains

#### 3.2 Secure Cookie Access Service
- Develop CookieAccessService with security validation
- Implement session extraction and validation logic
- Create domain-specific session handling
- Design secure access mechanisms

#### 3.3 Cookie Permission System
- Implement permission request workflow for cookie access
- Create persistent permission storage
- Design user consent management
- Add domain-specific permission handling

### Phase 4: AI Agent Integration (5-7 days)

#### 4.1 Agent Core Implementation
- Implement AiAgent with command processing capabilities
- Design context management system for browser state
- Create command routing and handling system
- Implement fallback mechanisms for different integration methods

#### 4.2 Agent Service and UI
- Develop AgentService for background processing
- Create response handling system for different response types
- Design service communication protocols
- Implement AgentHomePage UI with voice command support

#### 4.3 Search Visualization Interface
- Create SearchVisualizationActivity for search process display
- Implement search process tracking and visualization
- Design conversation display system
- Add source attribution display

### Phase 5: UI Automation Integration (4-6 days)

#### 5.1 Accessibility Service Implementation
- Implement ScreenInteractionService based on Android Accessibility API
- Configure service connection and event handling
- Design event processing pipeline
- Implement service monitoring system

#### 5.2 Finger Class Integration
- Adapt Blurr's Finger functionality for browser context
- Implement element interaction methods (click, text entry)
- Design element search and identification system
- Create accessibility permission handling

#### 5.3 Automation Service Integration
- Develop AutomationService for task execution
- Implement task execution workflow
- Create step-by-step automation processing
- Design visual feedback system for automation

### Phase 6: Workflow Engine Implementation (5-8 days)

#### 6.1 Workflow Engine Core
- Implement WorkflowEngine with execution capabilities
- Design node system for workflow components
- Create workflow execution pipeline
- Implement condition handling system

#### 6.2 Workflow Builder UI
- Develop WorkflowBuilder interface
- Create visual workflow editing capabilities
- Implement node addition and configuration
- Design workflow saving and loading system

#### 6.3 Workflow Storage
- Implement WorkflowStorage with persistent storage
- Create workflow serialization system
- Design loading and saving mechanisms
- Add workflow management capabilities

### Phase 7: Puter.js Integration Polish (3-5 days)

#### 7.1 Puter Configuration UI
- Create PuterConfigActivity for API management
- Implement configuration input and validation
- Design test connection functionality
- Add user feedback for configuration status

#### 7.2 Natural Language Search Visualization
- Implement SearchConversationView for conversation display
- Design conversation turn representation
- Create visual styling for different roles
- Implement conversation management system

#### 7.3 Search Visualization Activity
- Enhance SearchVisualizationActivity with conversation display
- Implement progressive search visualization
- Design turn addition system
- Create visual feedback for search process

#### 7.4 Puter Configuration Management
- Enhance PuterConfigManager with encryption
- Implement secure key generation
- Design encryption/decryption workflow
- Add secure storage mechanisms

### Phase 8: Integration and Polish (4-6 days)

#### 8.1 Unified Agent Interface
- Implement context updates from browser to agent
- Create unified response handling system
- Design cross-component communication
- Implement context synchronization

#### 8.2 Visual Feedback System
- Develop OverlayManager for visual notifications
- Implement overlay display and management
- Design visual styling and positioning
- Add auto-dismiss functionality

#### 8.3 Voice Interaction System
- Implement TTSManager for voice feedback
- Create text-to-speech processing
- Design voice command recognition
- Add voice interaction management

#### 8.4 Final Integration Testing
- Create comprehensive test suite
- Design browser integration test cases
- Implement cookie access verification
- Develop search functionality testing

## 3. Quality Assurance Plan

### 3.1 Testing Strategy
- Implement unit testing with high coverage
- Develop integration testing for component interactions
- Create performance testing suite
- Design security testing protocols

### 3.2 Key Metrics to Monitor
- Track memory usage metrics
- Monitor cookie access performance
- Measure tab switching performance
- Track workflow execution success rates
- Monitor UI automation reliability
- Measure API call success rates
- Track search accuracy metrics

### 3.3 Testing Scenarios
- Implement cookie access verification tests
- Create UI automation fallback scenarios
- Design workflow execution test cases
- Develop tab management performance tests
- Create natural language search validation
- Implement image generation testing

## 4. Deployment Plan

### 4.1 Release Strategy
- Plan alpha release with core browser and basic agent functionality
- Design beta release with UI automation and cookie access
- Prepare 1.0 release with complete workflow builder and polish

### 4.2 Required Permissions
- Identify internet access requirements
- Document audio recording permissions
- List accessibility service requirements
- Note foreground service permissions

### 4.3 User Onboarding
- Design first-run tutorial
- Create accessibility service enablement guide
- Develop command demonstration system
- Plan Puter.js API key setup instructions

### 4.4 Maintenance Plan
- Schedule monthly bug fix updates
- Plan quarterly feature updates
- Establish WebView update monitoring
- Create service integration change tracking
- Implement Puter.js API change monitoringich ->
                  val node = WorkflowEngine.WorkflowNode.NotionNode(
                      action = "create_page",
                      params = mapOf(
                          "title" to "",
                          "content" to ""
                      )
                  )
                  workflowCanvas.addNode(node)
              }
              .show()
      }
      
      // Other node addition methods...
      
      private fun saveWorkflow() {
          val name = findViewById<EditText>(R.id.workflowName).text.toString()
          if (name.isEmpty()) {
              Toast.makeText(this, "Please enter a workflow name", Toast.LENGTH_SHORT).show()
              return
          }
          
          val workflow = Workflow(
              id = currentWorkflow?.id ?: UUID.randomUUID().toString(),
              name = name,
              description = findViewById<EditText>(R.id.workflowDescription).text.toString(),
              nodes = workflowCanvas.getNodes(),
              updatedAt = System.currentTimeMillis()
          )
          
          WorkflowStorage.saveWorkflow(this, workflow)
          Toast.makeText(this, "Workflow saved", Toast.LENGTH_SHORT).show()
          
          // Return to previous screen if this was a new workflow
          if (currentWorkflow == null) {
              finish()
          }
      }
      
      private fun runWorkflow() {
          val nodes = workflowCanvas.getNodes()
          if (nodes.isEmpty()) {
              Toast.makeText(this, "Add nodes to your workflow first", Toast.LENGTH_SHORT).show()
              return
          }
          
          val workflow = Workflow(
              id = "temp",
              name = "Temporary Workflow",
              description = "Run from builder",
              nodes = nodes
          )
          
          // Get current cookies from browser
          val cookies = CookieManager.getAllCookies()
          
          // Execute workflow
          workflowEngine.execute(workflow, cookies)
          
          Toast.makeText(this, "Workflow execution started", Toast.LENGTH_SHORT).show()
      }
      
      private fun startNewWorkflow() {
          findViewById<TextView>(R.id.workflowName).setText("New Workflow")
          workflowCanvas.clear()
      }
      
      private fun loadWorkflow(workflowId: String) {
          val workflow = WorkflowStorage.loadWorkflow(this, workflowId) ?: run {
              Toast.makeText(this, "Workflow not found", Toast.LENGTH_SHORT).show()
              finish()
              return
          }
          
          currentWorkflow = workflow
          findViewById<TextView>(R.id.workflowName).setText(workflow.name)
          findViewById<TextView>(R.id.workflowDescription).setText(workflow.description)
          workflowCanvas.loadWorkflow(workflow)
      }
  }
  ```

#### 5.3 Workflow Storage
- Implement `WorkflowStorage.kt`:
  ```kotlin
  object WorkflowStorage {
      private const val WORKFLOWS_FILE = "workflows.json"
      
      fun saveWorkflow(context: Context, workflow: Workflow) {
          val workflows = loadAllWorkflows(context).toMutableList()
          
          // Replace existing workflow if it exists
          val existingIndex = workflows.indexOfFirst { it.id == workflow.id }
          if (existingIndex >= 0) {
              workflows[existingIndex] = workflow
          } else {
              workflows.add(workflow)
          }
          
          // Save to file
          val json = Json.encodeToString(WorkflowListSerializer, WorkflowList(workflows))
          context.openFileOutput(WORKFLOWS_FILE, Context.MODE_PRIVATE).use {
              it.write(json.toByteArray())
          }
      }
      
      fun loadWorkflow(context: Context, workflowId: String): Workflow? {
          return loadAllWorkflows(context).firstOrNull { it.id == workflowId }
      }
      
      fun loadAllWorkflows(context: Context): List<Workflow> {
          return try {
              context.openFileInput(WORKFLOWS_FILE).use { input ->
                  val json = input.bufferedReader().use { it.readText() }
                  Json.decodeFromString(WorkflowListSerializer, json).workflows
              }
          } catch (e: Exception) {
              emptyList()
          }
      }
      
      fun deleteWorkflow(context: Context, workflowId: String) {
          val workflows = loadAllWorkflows(context).filter { it.id != workflowId }
          saveAllWorkflows(context, workflows)
      }
      
      private fun saveAllWorkflows(context: Context, workflows: List<Workflow>) {
          val json = Json.encodeToString(WorkflowListSerializer, WorkflowList(workflows))
          context.openFileOutput(WORKFLOWS_FILE, Context.MODE_PRIVATE).use {
              it.write(json.toByteArray())
          }
      }
      
      // Serialization classes
      @Serializable
      data class WorkflowList(val workflows: List<Workflow>)
      
      private val WorkflowListSerializer = WorkflowList.serializer()
  }
  ```

### Phase 6: Integration and Polish (4-6 days)

#### 6.1 Unified Agent Interface
- Implement agent context updates:
  ```kotlin
  // In WebViewFragment
  override fun onPageFinished(view: WebView, url: String?) {
      currentUrl = url ?: currentUrl
      pageTitle = view.title
      
      // Extract page content for agent context
      view.evaluateJavascript(
          "(function() { " +
          "  return {" +
          "    title: document.title," +
          "    url: window.location.href," +
          "    content: document.body.innerText" +
          "  };" +
          "})()",
          { result ->
              try {
                  val json = JSONObject(result)
                  val context = AiAgent.AgentContext(
                      activeTabUrl = json.getString("url"),
                      activeTabTitle = json.getString("title"),
                      cookies = CookieManager.getAllCookies(),
                      pageContent = json.getString("content")
                  )
                  AgentService.updateContext(requireContext(), context)
              } catch (e: Exception) {
                  Log.e("WebViewFragment", "Error extracting page context", e)
              }
          }
      )
  }
  ```

- Implement agent response handling:
  ```kotlin
  // In AgentService
  private fun handleAgentResponse(response: AiAgent.AgentResponse, originalIntent: Intent) {
      when(response) {
          is AiAgent.AgentResponse.Success -> {
              TTSManager.speak(response.message)
              sendResultToUI(originalIntent, response.message)
          }
          is AiAgent.AgentResponse.Error -> {
              TTSManager.speak("Error: ${response.message}")
              sendErrorToUI(originalIntent, response.message)
          }
          is AiAgent.AgentResponse.RequiresAutomation -> {
              TTSManager.speak(response.message)
              // Start UI automation
              AutomationService.executeTask(this, response.task)
              
              // Show visual feedback
              OverlayManager.show(this, "Performing UI automation: ${response.message}")
          }
      }
  }
  ```

#### 6.2 Visual Feedback System
- Implement `OverlayManager.kt`:
  ```kotlin
  object OverlayManager {
      private var overlayView: HighlightView? = null
      
      fun show(context: Context, message: String) {
          if (overlayView != null) return
          
          val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
          val params = WindowManager.LayoutParams(
              WindowManager.LayoutParams.MATCH_PARENT,
              WindowManager.LayoutParams.WRAP_CONTENT,
              WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
              WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
              PixelFormat.TRANSLUCENT
          ).apply {
              gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
              y = 100 // Offset from top
          }
          
          overlayView = HighlightView(context).apply {
              setMessage(message)
              setOnClickListener { hide() }
          }
          
          windowManager.addView(overlayView, params)
          
          // Auto-hide after 3 seconds
          Handler(Looper.getMainLooper()).postDelayed({ hide() }, 3000)
      }
      
      fun hide() {
          overlayView?.let { view ->
              val windowManager = view.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
              windowManager.removeView(view)
              overlayView = null
          }
      }
      
      class HighlightView(context: Context) : FrameLayout(context) {
          private val textView = TextView(context).apply {
              setBackgroundColor(Color.parseColor("#CC000000"))
              setTextColor(Color.WHITE)
              gravity = Gravity.CENTER
              textSize = 16f
              setPadding(32, 16, 32, 16)
              alpha = 0.9f
          }
          
          init {
              setBackgroundColor(Color.TRANSPARENT)
              addView(textView)
          }
          
          fun setMessage(message: String) {
              textView.text = message
          }
      }
  }
  ```

#### 6.3 Voice Interaction System
- Implement `TTSManager.kt`:
  ```kotlin
  object TTSManager {
      private var textToSpeech: TextToSpeech? = null
      
      fun initialize(context: Context) {
          textToSpeech = TextToSpeech(context) { status ->
              if (status == TextToSpeech.SUCCESS) {
                  textToSpeech?.language = Locale.getDefault()
              }
          }
      }
      
      fun speak(text: String) {
          textToSpeech?.speak(
              text,
              TextToSpeech.QUEUE_ADD,
              null,
              "agent_speech_${System.currentTimeMillis()}"
          )
      }
      
      fun shutdown() {
          textToSpeech?.stop()
          textToSpeech?.shutdown()
          textToSpeech = null
      }
  }
  ```

- Implement `VoiceRecognizer.kt`:
  ```kotlin
  object VoiceRecognizer {
      fun startListening(activity: AppCompatActivity, callback: (String) -> Unit) {
          val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
              putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
              putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
              putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now")
          }
          
          try {
              activity.startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
          } catch (e: ActivityNotFoundException) {
              Toast.makeText(activity, "Your device doesn't support speech recognition", Toast.LENGTH_SHORT).show()
          }
      }
      
      fun handleActivityResult(
          requestCode: Int, 
          resultCode: Int, 
          data: Intent?, 
          callback: (String?) -> Unit
      ) {
          if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK && data != null) {
              val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
              callback(result?.get(0))
          } else {
              callback(null)
          }
      }
      
      private const val REQUEST_CODE_SPEECH_INPUT = 100
  }
  ```

#### 6.4 Final Integration Testing
- Create comprehensive test cases:
  ```kotlin
  class BrowserIntegrationTest {
      @Test
      fun testCookieAccessForGmail() {
          // Setup: Open Gmail in browser
          val browser = BrowserTestHelper.launchBrowser()
          browser.loadUrl("https://mail.google.com")
          
          // Wait for login
          Thread.sleep(5000)
          
          // Verify cookie access
          val cookies = CookieManager.getCookieForDomain("mail.google.com")
          assertNotNull(cookies)
          assertTrue(cookies.contains("SID="))
          
          // Verify agent can access cookies
          val session = CookieAccessService.getValidSessionFor("mail.google.com", context)
          assertNotNull(session)
      }
      
      @Test
      fun testNotionPageCreation() {
          // Setup: Log into Notion
          val browser = BrowserTestHelper.launchBrowser()
          browser.loadUrl("https://www.notion.so")
          
          // Wait for login
          Thread.sleep(5000)
          
          // Create agent context
          val context = AiAgent.AgentContext(
              activeTabUrl = "https://www.notion.so",
              activeTabTitle = "Notion",
              cookies = CookieManager.getAllCookies(),
              pageContent = "Notion homepage"
          )
          
          // Process command
          val agent = AiAgent(context)
          val response = agent.processCommand("create meeting notes")
          
          // Verify response
          assertTrue(response is AiAgent.AgentResponse.Success)
          assertTrue(response.message.contains("Created"))
      }
      
      @Test
      fun testWorkflowExecution() {
          // Create test workflow
          val workflow = Workflow(
              id = "test",
              name = "Test Workflow",
              description = "Test workflow",
              nodes = listOf(
                  WorkflowEngine.WorkflowNode.NotionNode(
                      action = "create_page",
                      params = mapOf("title" to "Test Page")
                  )
              )
          )
          
          // Save workflow
          WorkflowStorage.saveWorkflow(context, workflow)
          
          // Execute workflow
          val engine = WorkflowEngine(context, CookieAccessService)
          engine.execute(workflow, CookieManager.getAllCookies())
          
          // Verify page was created (would need Notion API verification)
          // This is simplified for the test
          assertTrue(true)
      }
  }
  ```

## 3. Quality Assurance Plan

### 3.1 Testing Strategy
- **Unit Tests**: 80%+ coverage for all core components
- **Integration Tests**: Verify browser, agent, and automation integration
- **Performance Tests**: 
  - Measure memory usage with 10+ tabs
  - Test cookie access speed
  - Measure workflow execution time
- **Security Tests**:
  - Verify cookie access restrictions
  - Test permission handling
  - Validate no cross-domain cookie access

### 3.2 Key Metrics to Monitor
- Memory usage (should be comparable to Chrome with same number of tabs)
- Cookie access time (should be < 50ms)
- Tab switching time (should be < 200ms)
- Workflow execution success rate (target 95%+)
- UI automation success rate (target 85%+)

### 3.3 Testing Scenarios
1. **Cookie Access Verification**
   - User logs into Gmail
   - Agent detects session and accesses cookies
   - Agent creates email using cookie-based API

2. **UI Automation Fallback**
   - User logs into a service that blocks cookie access
   - Agent falls back to UI automation
   - User sees visual feedback of automation steps

3. **Workflow Execution**
   - User creates workflow with Notion and Gmail nodes
   - User executes workflow
   - Both services are updated correctly

4. **Tab Management**
   - User opens 10+ tabs
   - Memory usage stays reasonable
   - Background tabs are properly discarded

## 4. Deployment Plan

### 4.1 Release Strategy
- **Alpha Release**: Core browser + basic agent functionality
- **Beta Release**: Add UI automation + cookie access
- **1.0 Release**: Complete workflow builder + polish

### 4.2 Required Permissions
- Internet access (for browsing)
- Record audio (for voice commands)
- Accessibility service (for UI automation)
- Foreground service (for agent operation)

### 4.3 User Onboarding
- First-run tutorial explaining capabilities
- Step-by-step guide to enable accessibility service
- Demonstration of basic commands and workflows

### 4.4 Maintenance Plan
- Monthly updates for bug fixes
- Quarterly feature updates
- Monitor WebView updates from Android team
- Track cookie access changes in major services (Gmail, Notion)