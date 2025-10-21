@echo off
cls
echo MyAgenticBrowser GitHub Actions Trigger
echo ======================================
echo.

echo Checking for Python...
python --version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Python is not installed or not in PATH
    echo Please install Python 3 and try again.
    echo.
    pause
    exit /b 1
) else (
    echo OK: Python is installed
)

echo.
echo Checking for required Python packages...
pip show requests >nul 2>&1
if %errorlevel% neq 0 (
    echo Installing requests package...
    pip install requests
    if %errorlevel% neq 0 (
        echo.
        echo ERROR: Failed to install requests package
        echo Please install it manually: pip install requests
        echo.
        pause
        exit /b 1
    )
) else (
    echo OK: requests package is installed
)

echo.
echo Running GitHub Actions trigger script...
echo.

python trigger_github_action.py %*

if %errorlevel% equ 0 (
    echo.
    echo SUCCESS: Script completed successfully!
) else (
    echo.
    echo ERROR: Script failed with exit code %errorlevel%
)

echo.
pause