@echo off
echo Building MyAgenticBrowser APK using py script...
echo ======================================================

py build_apk.py

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Build completed successfully!
    echo You can find the APK at: app\build\outputs\apk\debug\app-debug.apk
) else (
    echo.
    echo Build failed. Please check the error messages above.
)

pause