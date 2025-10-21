# Building MyAgenticBrowser

This document explains how to build and run the MyAgenticBrowser Android app.

## Prerequisites

Before building the app, you need to have the following installed:

1. **Java Development Kit (JDK)** - Version 8 or higher
2. **Android SDK** - With API level 28 or higher
3. **Android Studio** (optional but recommended) - For easier development and debugging

## Setting Up the Environment

1. Install the Android SDK or Android Studio (which includes the SDK)
2. Set the `ANDROID_HOME` environment variable to point to your Android SDK installation directory
3. Add the Android SDK build-tools to your PATH:
   - Windows: `%ANDROID_HOME%\build-tools\<version>`
   - macOS/Linux: `$ANDROID_HOME/build-tools/<version>`

## Building the App

### Method 1: Using Gradle Wrapper (Recommended)

If you have Gradle installed and properly configured:

```bash
# On Windows
.\gradlew.bat assembleDebug

# On macOS/Linux
./gradlew assembleDebug
```

The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### Method 2: Using Android Studio

1. Open Android Studio
2. Select "Open an existing Android Studio project"
3. Navigate to the project directory and select it
4. Wait for Gradle to sync the project
5. Click on "Build" → "Build Bundle(s) / APK(s)" → "Build APK(s)"
6. The APK will be generated and you can find it in the outputs directory

## Installing the App

Once you have the APK, you can install it on your Android device:

### Method 1: Using ADB

Connect your Android device to your computer via USB and enable USB debugging:

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Method 2: Manual Installation

1. Transfer the APK file to your Android device (via email, cloud storage, etc.)
2. On your Android device, locate the APK file and tap on it
3. Follow the prompts to install the app

## Troubleshooting

### Gradle Issues

If you encounter Gradle-related issues:

1. Make sure you're using a compatible Gradle version (7.6.1 or higher)
2. Clean the project: `./gradlew clean`
3. Rebuild the project: `./gradlew build`

### Missing Dependencies

If you get errors about missing dependencies:

1. Ensure you have an internet connection
2. Check that your Android SDK is up to date
3. Verify that all required SDK packages are installed

### Permission Issues

If the app doesn't work properly:

1. Make sure all required permissions are granted in the app settings
2. Check that the app has permission to access the internet and storage

## Testing the App

After installing the app:

1. Launch the app from your device's app drawer
2. Configure the Puter.js connection through the settings menu
3. Start using the AI agent features by interacting with the chat interface

## Contributing

If you'd like to contribute to the project:

1. Fork the repository
2. Create a new branch for your feature
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## Support

For support, please open an issue on the project's GitHub repository or contact the development team.