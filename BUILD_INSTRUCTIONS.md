# Build Instructions for MyAgenticBrowser

This document provides detailed instructions on how to build and run the MyAgenticBrowser Android app.

## Prerequisites

Before building the app, you need to have the following installed:

1. **Java Development Kit (JDK)** - Version 8 or higher
2. **Gradle** - Version 7.6.1 or higher
3. **Android SDK** - With API level 28 or higher

## Installing Prerequisites

### Windows

1. **Install Java JDK**:
   - Download and install the latest JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/)
   - Set the `JAVA_HOME` environment variable to point to your JDK installation directory
   - Add `%JAVA_HOME%\bin` to your `PATH` environment variable

2. **Install Gradle**:
   - Download Gradle from [Gradle.org](https://gradle.org/releases/)
   - Extract the archive to a directory (e.g., `C:\Gradle`)
   - Set the `GRADLE_HOME` environment variable to point to your Gradle installation directory
   - Add `%GRADLE_HOME%\bin` to your `PATH` environment variable

3. **Install Android SDK**:
   - Download and install Android Studio from [developer.android.com](https://developer.android.com/studio)
   - During installation, make sure to install the Android SDK
   - Set the `ANDROID_HOME` environment variable to point to your Android SDK installation directory (usually `C:\Users\[Username]\AppData\Local\Android\Sdk`)
   - Add the following to your `PATH` environment variable:
     - `%ANDROID_HOME%\tools`
     - `%ANDROID_HOME%\platform-tools`
     - `%ANDROID_HOME%\build-tools\[version]`

### macOS

1. **Install Java JDK**:
   - Use Homebrew: `brew install openjdk`
   - Or download and install from [Adoptium](https://adoptium.net/)

2. **Install Gradle**:
   - Use Homebrew: `brew install gradle`

3. **Install Android SDK**:
   - Download and install Android Studio from [developer.android.com](https://developer.android.com/studio)
   - Set the `ANDROID_HOME` environment variable in your shell profile (`.bashrc`, `.zshrc`, etc.):
     ```bash
     export ANDROID_HOME=$HOME/Library/Android/sdk
     export PATH=$ANDROID_HOME/tools:$PATH
     export PATH=$ANDROID_HOME/platform-tools:$PATH
     ```

### Linux

1. **Install Java JDK**:
   - Ubuntu/Debian: `sudo apt install openjdk-11-jdk`
   - Fedora: `sudo dnf install java-11-openjdk-devel`

2. **Install Gradle**:
   - Ubuntu/Debian: `sudo apt install gradle`
   - Or download from [Gradle.org](https://gradle.org/releases/)

3. **Install Android SDK**:
   - Download and install Android Studio from [developer.android.com](https://developer.android.com/studio)
   - Set the `ANDROID_HOME` environment variable in your shell profile (`.bashrc`, `.zshrc`, etc.):
     ```bash
     export ANDROID_HOME=$HOME/Android/Sdk
     export PATH=$ANDROID_HOME/tools:$PATH
     export PATH=$ANDROID_HOME/platform-tools:$PATH
     ```

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

### Method 2: Using System Gradle

If you have Gradle installed and properly configured:

```bash
# On all platforms
gradle assembleDebug
```

The APK will be generated at: `app/build/outputs/apk/debug/app-debug.apk`

### Method 3: Using Android Studio

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