# Simple PowerShell script to build MyAgenticBrowser APK

Write-Host "MyAgenticBrowser Simple Build Script" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green

# Check if Java is installed
Write-Host "Checking for Java..." -ForegroundColor Cyan
try {
    java -version > $null 2>&1
    Write-Host "✓ Java is installed" -ForegroundColor Green
} catch {
    Write-Host "✗ Java is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Java and try again." -ForegroundColor Yellow
    exit 1
}

# Check if Gradle is installed
Write-Host "Checking for Gradle..." -ForegroundColor Cyan
try {
    gradle --version > $null 2>&1
    Write-Host "✓ Gradle is installed" -ForegroundColor Green
} catch {
    Write-Host "✗ Gradle is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Gradle and try again." -ForegroundColor Yellow
    exit 1
}

# Try to build the APK using Gradle wrapper first
Write-Host "Attempting to build APK using Gradle wrapper..." -ForegroundColor Cyan

# Check if Gradle wrapper script exists
if (Test-Path ".\gradlew.bat") {
    Write-Host "Found Gradle wrapper script, attempting to build..." -ForegroundColor Yellow
    .\gradlew.bat assembleDebug
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ APK built successfully using Gradle wrapper!" -ForegroundColor Green
        Write-Host "APK location: app\build\outputs\apk\debug\app-debug.apk" -ForegroundColor Yellow
        exit 0
    } else {
        Write-Host "✗ Failed to build APK using Gradle wrapper" -ForegroundColor Red
    }
} else {
    Write-Host "✗ Gradle wrapper script not found" -ForegroundColor Red
}

# Try to build the APK using system Gradle
Write-Host "Attempting to build APK using system Gradle..." -ForegroundColor Cyan

gradle assembleDebug
if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ APK built successfully using system Gradle!" -ForegroundColor Green
    Write-Host "APK location: app\build\outputs\apk\debug\app-debug.apk" -ForegroundColor Yellow
    exit 0
} else {
    Write-Host "✗ Failed to build APK using system Gradle" -ForegroundColor Red
}

Write-Host "Build process completed with issues." -ForegroundColor Yellow
Write-Host "Please check the error messages above and try to resolve them." -ForegroundColor Yellow
exit 1