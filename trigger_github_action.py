#!/usr/bin/env python3

"""
Script to trigger GitHub Actions workflow for building the MyAgenticBrowser APK
"""

import requests
import json
import os
import sys
import argparse
from getpass import getpass

def trigger_workflow(repo_owner, repo_name, token, workflow_name="Build Debug APK"):
    """
    Trigger a GitHub Actions workflow
    
    Args:
        repo_owner (str): GitHub username or organization name
        repo_name (str): Repository name
        token (str): GitHub personal access token
        workflow_name (str): Name of the workflow to trigger
    
    Returns:
        bool: True if successful, False otherwise
    """
    # GitHub API endpoint for triggering workflow
    url = f"https://api.github.com/repos/{repo_owner}/{repo_name}/actions/workflows/{workflow_name}/dispatches"
    
    # Headers for the request
    headers = {
        "Accept": "application/vnd.github.v3+json",
        "Authorization": f"token {token}"
    }
    
    # Data for the request
    data = {
        "ref": "main"  # Branch to trigger on
    }
    
    try:
        # Send POST request to trigger workflow
        response = requests.post(url, headers=headers, data=json.dumps(data))
        
        if response.status_code == 204:
            print("✓ Workflow triggered successfully!")
            return True
        else:
            print(f"✗ Failed to trigger workflow. Status code: {response.status_code}")
            print(f"Response: {response.text}")
            return False
            
    except Exception as e:
        print(f"✗ Error triggering workflow: {str(e)}")
        return False

def main():
    parser = argparse.ArgumentParser(description="Trigger GitHub Actions workflow for MyAgenticBrowser APK build")
    parser.add_argument("--owner", help="GitHub repository owner (username or organization)")
    parser.add_argument("--repo", help="GitHub repository name")
    parser.add_argument("--token", help="GitHub personal access token")
    parser.add_argument("--workflow", default="Build Debug APK", help="Workflow name to trigger")
    
    args = parser.parse_args()
    
    print("MyAgenticBrowser GitHub Actions Trigger Script")
    print("=" * 50)
    
    # Get repository owner if not provided
    if not args.owner:
        args.owner = input("Enter GitHub repository owner (username or organization): ").strip()
    
    if not args.owner:
        print("✗ Repository owner is required")
        return 1
    
    # Get repository name if not provided
    if not args.repo:
        args.repo = input("Enter GitHub repository name: ").strip()
    
    if not args.repo:
        print("✗ Repository name is required")
        return 1
    
    # Get GitHub token if not provided
    if not args.token:
        args.token = getpass("Enter GitHub personal access token: ").strip()
    
    if not args.token:
        print("✗ GitHub token is required")
        return 1
    
    print(f"\nTriggering workflow '{args.workflow}' for {args.owner}/{args.repo}...")
    
    success = trigger_workflow(args.owner, args.repo, args.token, args.workflow)
    
    if success:
        print("\n✓ Workflow triggered successfully!")
        print("Go to https://github.com/{}/{}/actions to monitor the build progress.".format(args.owner, args.repo))
        print("Once completed, you can download the APK from the Artifacts section.")
        return 0
    else:
        print("\n✗ Failed to trigger workflow")
        return 1

if __name__ == "__main__":
    sys.exit(main())