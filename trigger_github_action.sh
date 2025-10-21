#!/bin/bash

# MyAgenticBrowser GitHub Actions Trigger Script
# For macOS/Linux users

echo "MyAgenticBrowser GitHub Actions Trigger"
echo "======================================"
echo ""

echo "Checking for Python..."
if ! command -v python3 &> /dev/null; then
    echo ""
    echo "ERROR: Python 3 is not installed or not in PATH"
    echo "Please install Python 3 and try again."
    echo ""
    exit 1
else
    echo "OK: Python 3 is installed"
fi

echo ""
echo "Checking for required Python packages..."
if ! python3 -c "import requests" &> /dev/null; then
    echo "Installing requests package..."
    pip3 install requests
    if [ $? -ne 0 ]; then
        echo ""
        echo "ERROR: Failed to install requests package"
        echo "Please install it manually: pip3 install requests"
        echo ""
        exit 1
    fi
else
    echo "OK: requests package is installed"
fi

echo ""
echo "Running GitHub Actions trigger script..."
echo ""

python3 trigger_github_action.py "$@"

if [ $? -eq 0 ]; then
    echo ""
    echo "SUCCESS: Script completed successfully!"
else
    echo ""
    echo "ERROR: Script failed"
fi

echo ""
read -p "Press Enter to continue..."