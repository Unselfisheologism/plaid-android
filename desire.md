desire.md
Agentic Browser Mobile Application - Vision and Requirements
1. Core Vision
Create a truly agentic mobile browser for Android that seamlessly integrates AI capabilities with web browsing, enabling the AI to understand context, access session cookies for external services, perform UI automation, and execute workflows - all within a single, efficient application. Crucially, all AI capabilities must be powered through Puter.js integration rather than direct API calls to traditional AI providers.

2. Fundamental Requirements
2.1 Architecture Requirements
NOT a browser engine fork (no Bromite, Ncromite, Kiwi, or Chromium modifications)
Single unified application (not multiple separate apps that need synchronization)
WebView-based container using Android's built-in WebView component
No Node.js dependency (avoid nodejs-mobile complexity)
No n8n fork (build lightweight workflow engine from scratch)
Memory efficient (comparable to real browser tab management)
Puter.js as the sole AI infrastructure provider (replacing all traditional AI API providers)
2.2 Technical Capabilities
2.2.1 Browser Capabilities
Tab management with ViewPager2 (3-5 active tabs maximum)
Tab discarding mechanism to save memory when tabs become inactive
Proper cookie management using Android's CookieManager
Full access to session cookies for external service integration
Ability to detect when user is logged into services (Gmail, Notion, etc.)
Background tab loading capabilities
2.2.2 AI Agent Capabilities
Complete migration from traditional AI API providers to Puter.js
All AI features (chat, reasoning, analysis) must route through Puter.js
No direct API keys for OpenAI, Anthropic, Google, etc. should be stored or used
Puter.js configuration must handle all AI provider endpoints and authentication
AI agent as default homepage/new tab page
Context awareness of current browser tab content
Ability to switch between tabs programmatically
Unified agent that can choose between cookie-based and UI automation methods
Automatic fallback from cookie-based to UI automation when needed
Voice command support for agent activation
Text-to-image generation must be fully integrated with Puter.js
All image generation requests must go through Puter.js infrastructure
No direct API calls to Stable Diffusion, DALL-E, or other image generation services
2.2.3 AI-Powered Web Search Replacement
Complete replacement of all web search APIs (Tavily, Cloudsway, local search, Baidu, Bing, etc.)
Implementation of Perplexity Sonar model intercommunication system:
The main AI chat model must communicate with Perplexity Sonar models using natural language only
No programmatic API calls or structured data formats for search queries
The chat model formulates specific questions in natural language to the Perplexity Sonar model
Perplexity Sonar model responds in natural language with search results
The main AI model processes these natural language responses as web search results
The entire interaction must feel like a conversation between two AI models
Implementation requirements for the inter-model communication:
Clear separation between "chat model" and "search model" contexts
Visual indication when search queries are being processed
Ability for the user to view the natural language conversation between models
Search queries must be formulated precisely by the chat model to get relevant results
The chat model must properly synthesize and cite information from search results
2.2.4 UI Automation Capabilities
Direct integration of Blurr's UI automation functionality (specifically Finger.kt)
AccessibilityService implementation for UI element interaction
Ability to interact with UI elements within the browser context
No background automation requirement (user must be present)
Visual feedback for automation actions (TTS announcements, overlays)
Foreground service implementation with persistent notification
2.2.5 Workflow Engine Capabilities
Lightweight workflow engine built in Kotlin (not n8n port)
Mobile-optimized workflow builder UI
Support for key service integrations (Gmail, Notion as minimum)
Ability to execute workflows using session cookies from browser
CRUD operations for workflows
Visual workflow editor
Simple node system with minimal overhead
AI-powered workflow suggestions using Puter.js integration
Workflow suggestions generated through natural language understanding
No direct API calls to external workflow suggestion services
2.3 Security Requirements
No cross-domain UI manipulation (respects browser security model)
Explicit user permission for AccessibilityService
Secure cookie access with domain-specific restrictions
User consent for session cookie usage with external services
No cookie sharing between unrelated domains
No attempt to bypass Android security model
Secure handling of Puter.js credentials:
All Puter.js API keys must be encrypted at rest
No hard-coded credentials in source code
User-managed credential storage with secure Android Keystore
Clear separation between user's Puter.js credentials and application code
2.4 Performance Requirements
Memory usage comparable to standard browser (100 tabs scenario)
Tab switching under 200ms
Cookie access under 50ms
Workflow execution with minimal latency
No noticeable performance degradation with 10+ tabs
AI interaction performance:
Natural language search queries must be processed within 3 seconds
Perplexity Sonar model responses must be integrated within 5 seconds
Text-to-image generation requests must complete within 15 seconds
3. User Experience Requirements
3.1 Core User Flows
Browsing with AI assistance:
User opens browser → sees AI agent homepage
User can type voice/text commands to agent
Agent understands context from current tab
Agent can take actions using cookies or UI automation
When web search is needed:
Agent formulates natural language query to Perplexity Sonar model
Agent processes the natural language response as search results
Agent synthesizes information and presents to user
External service integration:
User logs into Gmail/Notion in browser
Agent detects login and accesses session cookies
User commands agent to interact with service ("Create Notion page")
Agent uses cookies to interact at "API speed" without UI automation
Workflow creation and execution:
User opens workflow builder from agent interface
User creates workflow with visual editor
Workflow can access browser session cookies
User executes workflow directly from browser
UI automation fallback:
When cookie access isn't possible
Agent uses AccessibilityService to interact with UI
User sees visual feedback of automation actions
Clear indication when UI automation is being used
AI-powered search experience:
User asks question requiring current information
Agent explains it's consulting its "research assistant" (Perplexity Sonar)
User sees natural language conversation between models
Agent presents synthesized answer with sources
3.2 Interface Requirements
Clean, minimal browser UI with AI agent as central element
Tab management similar to modern browsers
Workflow builder accessible from agent interface
Visual indicators showing when agent is active
TTS feedback for agent actions
Screen overlays for UI automation steps
Persistent notification when agent is active
Natural language search visualization:
Clear visual separation between main chat and search model conversation
Expandable/collapsible search conversation history
Source attribution for search-based information
Ability to request more detailed search results
4. Constraints and Limitations
4.1 Technical Constraints
Must work within Android security model (no root required)
No background UI automation (Android security limitation)
Limited to Android platform (initially)
Must use AndroidX components
Must target API level 28+ (Android 9+)
Must support both phone and tablet layouts
Puter.js integration constraints:
All AI capabilities must work through Puter.js infrastructure
No fallback to direct provider APIs if Puter.js is unavailable
Must handle Puter.js rate limits and error conditions gracefully
4.2 Known Limitations
Cannot perform UI automation when app is in background
Cannot access cookies from other apps (Android security)
UI automation requires explicit user permission
Cookie-based integration only works for same-domain services
No cross-site UI manipulation (security restriction)
Natural language search limitations:
Search quality depends on how well the chat model formulates queries
May require multiple iterations to get optimal search results
Cannot guarantee same precision as structured API search requests
5. Non-Requirements
No desktop version needed (Android only)
No separate Blurr app integration
No n8n compatibility required
No Node.js runtime needed
No browser engine modifications
No background service automation
No attempt to bypass Android security model
No direct integration with traditional AI providers:
No OpenAI API keys
No Anthropic Claude integration
No Google Gemini direct access
No direct Hugging Face model access
No direct search API integrations (Bing, Tavily, etc.)
No programmatic search API usage:
All search must happen through natural language model-to-model communication
No JSON/XML structured search responses
No direct parsing of search engine results