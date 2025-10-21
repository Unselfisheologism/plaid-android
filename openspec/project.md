# Project Context

## Purpose
Create a truly agentic mobile browser for Android that seamlessly integrates AI capabilities with web browsing, enabling the AI to understand context, access session cookies for external services, perform UI automation, and execute workflows - all within a single, efficient application. Crucially, all AI capabilities must be powered through Puter.js integration rather than direct API calls to traditional AI providers. The application will be built as a WebView-based browser with tab management, cookie access, UI automation capabilities, and workflow execution, all unified in a single security context. The UI will follow a mobile-first design with accessibility in mind, featuring swipe-up chat interfaces, tab previews, workflow visualization, and clear indicators for active/dormant states.

## Tech Stack
- Kotlin (primary language for Android development)
- Android WebView component for browser functionality
- AndroidX libraries (ViewPager2 for tab management)
- Android AccessibilityService for UI automation
- Android CookieManager for session cookie access
- Puter.js SDK for AI capabilities
- JSON serialization for workflow storage
- Android Room database (optional for advanced storage needs)

## Project Conventions

### Code Style
- Follow Kotlin official coding conventions (kotlin.code.style=official)
- Use 4-space indentation
- Use PascalCase for class names, camelCase for functions and variables
- Use descriptive names that clearly indicate purpose
- Implement proper error handling with try-catch blocks where appropriate
- Use meaningful comments for complex logic
- Follow Android recommended practices for Activity/Fragment lifecycles

### Architecture Patterns
- MVVM (Model-View-ViewModel) pattern for UI components
- Repository pattern for data access layers
- Single Activity with multiple Fragments for different browser tabs
- Service-based architecture for background operations (AccessibilityService, AgentService)
- Dependency injection for managing component dependencies
- Separation of concerns between browser functionality, AI agent, UI automation, and workflow engine

### Testing Strategy
- Unit tests for core business logic (80%+ coverage)
- Integration tests for browser, agent, and automation component interactions
- Performance tests for memory usage with multiple tabs
- Cookie access speed tests (target < 50ms)
- Tab switching performance tests (target < 200ms)
- UI automation reliability tests (target 85%+ success rate)
- Workflow execution success rate tests (target 95%+)

### Git Workflow
- Use feature branches for new functionality
- Follow conventional commits pattern (feat:, fix:, refactor:, etc.)
- Create pull requests for all non-trivial changes
- Use semantic versioning for releases
- Include issue references in commit messages when applicable

## Domain Context
This project combines several complex domains:
- Web browsing with tab management and memory optimization
- AI agent functionality with natural language processing
- UI automation using Android's AccessibilityService
- Session cookie management for external service integration
- Workflow execution with visual workflow builder
- Mobile optimization for performance and battery life

The application must work within Android's security model without requiring root access, respecting user privacy while enabling powerful automation capabilities.

## UI Context
The application features a mobile-first interface with several key UI elements:
- Swipe-up chat interface for AI interactions with user message identification and response display
- Text input with attachment (+), settings, and follow-up capabilities
- Vertical workflow interface with nodes optimized for mobile interaction
- Multimodal content canvas displaying images, videos, charts, infographics, spreadsheets, and audio
- Video player with timeline and editing features
- Slash command interface (/search, /ask, /automate, /expert) for quick AI interactions
- Slide-out menu with account, settings, integrations, workflows, agents, support, and page summarization options
- Expert agent interface with checkpoints showing AI task progress
- Tab preview system with 6 tabs in a 2x3 grid, showing webpage titles and previews
- User/agent indicators and active/dormant status indicators (green/yellow) for tabs
- Always-present bottom oval shape (swipe-up popup) and top menu bar with three-line navigation icon
- Accessibility-focused design with large buttons, clear labels, and simple navigation

## Important Constraints
- Must work within Android security model (no root required)
- No background UI automation (Android security limitation)
- Limited to Android platform (API level 28+ minimum)
- Must use AndroidX components
- Must support both phone and tablet layouts
- Puter.js integration constraints: all AI capabilities must work through Puter.js infrastructure
- No fallback to direct provider APIs if Puter.js is unavailable
- Cannot perform UI automation when app is in background
- UI automation requires explicit user permission
- Cookie-based integration only works for same-domain services
- No cross-site UI manipulation (security restriction)

## External Dependencies
- Puter.js API for all AI capabilities (chat, reasoning, analysis, image generation)
- Android system services (WebView, AccessibilityService, CookieManager)
- Android speech recognition APIs for voice commands
- Android Text-to-Speech for voice feedback
- Android Keystore system for secure credential storage
- Android foreground services for persistent agent operation
