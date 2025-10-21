@echo off
echo MyAgenticBrowser Build Script
echo ============================
echo.

echo Checking for Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ✗ Java is not installed or not in PATH
    echo Please install Java and try again.
    pause
    exit /b 1
) else (
    echo ✓ Java is installed
)

echo.
echo Checking for Gradle...
gradle --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ✗ Gradle is not installed or not in PATH
    echo Please install Gradle and try again.
    pause
    exit /b 1
) else (
    echo ✓ Gradle is installed
)

echo.
echo Attempting to build APK using Gradle wrapper...
if exist "gradlew.bat" (
    echo Found Gradle wrapper script, attempting to build...
    call gradlew.bat assembleDebug
    if %errorlevel% equ 0 (
        echo.
        echo ✓ APK built successfully using Gradle wrapper!
        echo APK location: app\build\outputs\apk\debug\app-debug.apk
        pause
        exit /b 0
    ) else (
        echo.
        echo ✗ Failed to build APK using Gradle wrapper
    )
) else (
    echo.
    echo ✗ Gradle wrapper script not found
)

echo.
echo Attempting to build APK using system Gradle...
gradle assembleDebug
if %errorlevel% equ 0 (
    echo.
    echo ✓ APK built successfully using system Gradle!
    echo APK location: app\build\outputs\apk\debug\app-debug.apk
    pause
    exit /b 0
) else (
    echo.
    echo ✗ Failed to build APK using system Gradle
)

echo.
echo Build process completed with issues.
echo Please check the error messages above and try to resolve them.
pause
exit /b 1