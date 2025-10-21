#!/usr/bin/env python3

"""
Simple build script for MyAgenticBrowser Android app
"""

import os
import sys
import subprocess
import shutil
from pathlib import Path

def check_prerequisites():
    """Check if all prerequisites are installed"""
    print("Checking prerequisites...")
    
    # Check if Java is installed
    try:
        subprocess.run(["java", "-version"], check=True, capture_output=True)
        print("✓ Java is installed")
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("✗ Java is not installed or not in PATH")
        return False
    
    # Check if Android SDK is installed
    android_home = os.environ.get("ANDROID_HOME")
    if not android_home:
        print("✗ ANDROID_HOME environment variable is not set")
        return False
    
    if not os.path.exists(android_home):
        print(f"✗ ANDROID_HOME path does not exist: {android_home}")
        return False
    
    print(f"✓ Android SDK found at: {android_home}")
    
    # Check if Gradle is installed
    try:
        subprocess.run(["gradle", "--version"], check=True, capture_output=True)
        print("✓ Gradle is installed")
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("✗ Gradle is not installed or not in PATH")
        # This is not fatal as we can use the Gradle wrapper
    
    return True

def build_with_gradle_wrapper():
    """Build the APK using Gradle wrapper"""
    print("Building APK with Gradle wrapper...")
    
    # Determine the Gradle wrapper command based on OS
    if os.name == 'nt':  # Windows
        gradle_cmd = "./gradlew.bat"
    else:  # Unix-like systems
        gradle_cmd = "./gradlew"
    
    # Check if Gradle wrapper exists
    if not os.path.exists(gradle_cmd):
        print(f"✗ Gradle wrapper not found: {gradle_cmd}")
        return False
    
    # Make sure the wrapper is executable on Unix-like systems
    if os.name != 'nt':
        try:
            os.chmod(gradle_cmd, 0o755)
        except OSError as e:
            print(f"Warning: Could not make {gradle_cmd} executable: {e}")
    
    # Run the build command
    try:
        result = subprocess.run([gradle_cmd, "assembleDebug"], check=True, capture_output=True, text=True)
        print("✓ APK built successfully")
        print("APK location: app/build/outputs/apk/debug/app-debug.apk")
        return True
    except subprocess.CalledProcessError as e:
        print(f"✗ Build failed with exit code {e.returncode}")
        print("STDOUT:", e.stdout)
        print("STDERR:", e.stderr)
        return False
    except FileNotFoundError:
        print(f"✗ Could not find Gradle wrapper: {gradle_cmd}")
        return False

def main():
    """Main function"""
    print("MyAgenticBrowser Build Script")
    print("=" * 30)
    
    # Check prerequisites
    if not check_prerequisites():
        print("\nPlease install the required prerequisites and try again.")
        sys.exit(1)
    
    print()
    
    # Build the APK
    if not build_with_gradle_wrapper():
        sys.exit(1)
    
    print("\nBuild completed successfully!")
    print("You can find the APK at: app/build/outputs/apk/debug/app-debug.apk")

if __name__ == "__main__":
    main()