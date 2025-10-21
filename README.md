# MyAgenticBrowser

An agentic mobile browser application that combines WebView-based browsing with Puter.js AI integration for enhanced automation capabilities.

## Features

- **WebView-based Browser Foundation**: Fast, memory-efficient browsing with tab management
- **Puter.js AI Integration**: All AI capabilities route through Puter.js infrastructure
- **Perplexity Sonar Web Search**: Natural language web search using Perplexity Sonar models through Puter.js
- **UI Automation**: Accessibility service integration for screen interaction
- **Workflow Engine**: Visual workflow builder for complex automation tasks
- **Cookie Management**: Secure cookie access for external service integration
- **Voice Commands**: Speech recognition and text-to-speech capabilities

## Architecture

The application follows a modular architecture with the following key components:

### 1. Browser Foundation
- WebView-based browsing engine
- Tab management system with ViewPager2
- Memory optimization through tab discarding
- Cookie management for session persistence

### 2. AI Integration
- Puter.js client for all AI capabilities
- Natural language search with Perplexity Sonar models
- Image generation and analysis
- Text-to-speech functionality

### 3. Automation Layer
- Accessibility service for UI interaction
- Screen interaction service for automation
- Finger class for element manipulation
- Overlay manager for visual feedback

### 4. Workflow Engine
- Visual workflow builder
- Node-based workflow system
- Persistent workflow storage
- Execution engine for automation tasks

## Requirements

- Android 8.0 (API level 28) or higher
- Puter.js account (browser-based authentication)
- Internet connectivity for AI services

## Installation

1. Clone the repository
2. Open in Android Studio
3. Build and run on device or emulator

### Alternative: Build with GitHub Actions (No Android Studio Required)

You can build the APK without installing Android Studio or the Android SDK by using GitHub Actions. This approach doesn't require setting up ANDROID_HOME or other local development tools, saving disk space and setup time.

1. Fork this repository to your GitHub account
2. Go to the "Actions" tab in your forked repository
3. Select the "Build App" workflow (unified workflow that builds both debug and release APKs)
4. Click "Run workflow"
5. Wait for the workflow to complete (usually 5-10 minutes)
6. Download the APK from the "Artifacts" section (both debug-apk and release-apk will be available)

**Alternative approach**: Use the trigger script from your local machine without any local Android tools installed:
1. Install Python 3 if you don't have it
2. Run `trigger_github_action.bat` (Windows) or `trigger_github_action.sh` (macOS/Linux)
3. Enter your GitHub credentials when prompted
4. The script will trigger the GitHub Actions workflow for you

For detailed instructions on using GitHub Actions, please see [GITHUB_ACTIONS_README.md](GITHUB_ACTIONS_README.md).

For detailed build options without local SDK setup, please see [BUILD_OPTIONS_NO_LOCAL_SDK.md](BUILD_OPTIONS_NO_LOCAL_SDK.md).

For detailed local build instructions, please see [README_BUILD.md](README_BUILD.md).

## Configuration

1. Launch the app
2. Navigate to Settings > AI Configuration
3. Authentication happens automatically through browser-based authentication when using Puter.js features
4. Test the connection

## Usage

### Basic Browsing
- Use the address bar to navigate to websites
- Tap the "+" button to open new tabs
- Swipe between tabs to switch contexts

### AI Agent Features
- Access the AI Agent through the menu
- Use voice commands for hands-free operation
- Perform searches with natural language queries

### Web Search with Perplexity Sonar
- Access the Search Visualization through the menu
- Enter natural language queries for web search
- View the conversation between AI models during search
- See sources and citations for search results

### Automation
- Enable accessibility service in device settings
- Use the workflow builder to create automation tasks
- Execute workflows with a single tap

## Security

- All AI capabilities route through Puter.js infrastructure
- No direct API keys for OpenAI, Anthropic, Google, etc. are stored or used
- Puter.js handles all AI provider endpoints and authentication internally
- Cookie access is restricted to user-granted permissions only

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Puter.js for providing the AI infrastructure
- Android Open Source Project for the WebView component
- All contributors who have helped with the development