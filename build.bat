@echo off
cls
echo MyAgenticBrowser Build Script
echo =============================
echo.

echo Checking for Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK 8 or higher and try again.
    echo.
    pause
    exit /b 1
) else (
    echo OK: Java is installed
)

echo.
echo Checking for Gradle...
gradle --version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo WARNING: Gradle is not installed or not in PATH
    echo Trying to use Gradle wrapper instead...
) else (
    echo OK: Gradle is installed
)

echo.
echo Attempting to build APK...
echo.

if exist "gradlew.bat" (
    echo Using Gradle wrapper...
    call gradlew.bat assembleDebug
) else (
    echo Using system Gradle...
    gradle assembleDebug
)

if %errorlevel% equ 0 (
    echo.
    echo SUCCESS: APK built successfully!
    echo.
    echo APK location: app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo To install on your Android device:
    echo   1. Connect your device via USB
    echo   2. Enable USB debugging
    echo   3. Run: adb install app\build\outputs\apk\debug\app-debug.apk
) else (
    echo.
    echo ERROR: Failed to build APK
    echo.
    echo Please check the error messages above.
    echo Make sure you have all prerequisites installed:
    echo   - Java JDK 8 or higher
    echo   - Android SDK with API level 28 or higher
    echo   - ANDROID_HOME environment variable set
)

echo.
pause