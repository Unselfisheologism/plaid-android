# GitHub Actions for MyAgenticBrowser

This document explains how to use GitHub Actions to build the MyAgenticBrowser Android app without needing to install Android Studio or other heavy development tools on your local machine.

## Available Workflows

### 1. Build Debug APK (`build-debug-apk.yml`)
- **Trigger**: Automatically runs on every push or pull request to `main` or `master` branches
- **Purpose**: Builds a debug APK for testing
- **Output**: Uploads the debug APK as an artifact that can be downloaded

### 2. Build All APKs (`build-all-apks.yml`)
- **Trigger**: Automatically runs on every push or pull request to `main` or `master` branches
- **Purpose**: Builds both debug and release APKs
- **Output**: Uploads both debug and release APKs as artifacts

### 3. Build Signed APK (`build-signed-apk.yml`)
- **Trigger**: Runs when a tag starting with `v` is pushed (e.g., `v1.0.0`)
- **Purpose**: Builds a signed release APK for distribution
- **Output**: Uploads the signed APK as an artifact

## Setting Up GitHub Actions

### Prerequisites
1. A GitHub account
2. This repository forked to your GitHub account or transferred to your organization

### Enabling Workflows
1. Go to your repository on GitHub
2. Navigate to the "Actions" tab
3. You should see the workflows listed above
4. Click on a workflow to view its details
5. Click "Run workflow" to manually trigger it

## Using the Workflows

### Building a Debug APK
1. Go to the "Actions" tab in your repository
2. Select "Build Debug APK" from the workflow list
3. Click "Run workflow"
4. Wait for the workflow to complete (usually takes 5-10 minutes)
5. Once completed, click on the workflow run to view details
6. In the "Artifacts" section, download the `debug-apk` artifact
7. Extract the ZIP file to get the APK

### Building Both Debug and Release APKs
1. Go to the "Actions" tab in your repository
2. Select "Build All APKs" from the workflow list
3. Click "Run workflow"
4. Wait for the workflow to complete
5. Once completed, click on the workflow run to view details
6. In the "Artifacts" section, download both `debug-apk` and `release-apk` artifacts
7. Extract the ZIP files to get the APKs

### Building a Signed Release APK
1. First, you need to set up signing secrets in your repository:
   - Go to "Settings" → "Secrets and variables" → "Actions"
   - Add the following secrets:
     - `KEYSTORE_BASE64`: Base64 encoded keystore file
     - `KEYSTORE_PASSWORD`: Password for the keystore
     - `KEY_ALIAS`: Alias for the key
     - `KEY_PASSWORD`: Password for the key
2. Create a tag starting with `v` (e.g., `v1.0.0`) and push it to GitHub
3. Go to the "Actions" tab in your repository
4. Select "Build Signed APK" from the workflow list
5. Click "Run workflow"
6. Wait for the workflow to complete
7. Once completed, click on the workflow run to view details
8. In the "Artifacts" section, download the `signed-apk` artifact
9. Extract the ZIP file to get the signed APK

## Converting Keystore to Base64

To create the `KEYSTORE_BASE64` secret:

On macOS/Linux:
```bash
base64 -i your_keystore.jks -o keystore_base64.txt
```

On Windows:
```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("your_keystore.jks")) > keystore_base64.txt
```

Then copy the contents of `keystore_base64.txt` to the `KEYSTORE_BASE64` secret in GitHub.

## Downloading Artifacts

After a workflow completes successfully:

1. Go to the "Actions" tab
2. Click on the completed workflow run
3. Scroll down to the "Artifacts" section
4. Click on the artifact you want to download (e.g., `debug-apk`)
5. Save the ZIP file to your computer
6. Extract the ZIP file to get the APK

## Installing the APK

Once you have downloaded the APK:

1. Transfer the APK file to your Android device (via email, cloud storage, etc.)
2. On your Android device, locate the APK file and tap on it
3. If prompted, enable "Install from unknown sources" in your device settings
4. Follow the prompts to install the app

## Troubleshooting

### Workflow Fails
- Check the logs in the workflow run for specific error messages
- Common issues:
  - Missing dependencies in `build.gradle`
  - Incorrect Java/Gradle versions
  - Network issues during dependency downloads

### APK Not Found
- Make sure the workflow completed successfully
- Check that the correct path is specified in the `upload-artifact` step
- Verify that the APK was actually built by checking the build logs

### Signing Issues
- Ensure all signing secrets are correctly set
- Verify that the keystore password, key alias, and key password are correct
- Check that the keystore file is properly base64 encoded

## Customizing Workflows

You can customize the workflows by editing the YAML files in `.github/workflows/`:

- Change trigger conditions (branches, tags, events)
- Modify build steps
- Add additional testing or validation steps
- Change artifact upload paths
- Add notifications (Slack, email, etc.)

## Benefits of Using GitHub Actions

1. **No Local Setup Required**: No need to install Android Studio, JDK, or Gradle
2. **Consistent Builds**: All builds happen in the same environment
3. **Automated Testing**: Can integrate automated tests into the workflow
4. **Easy Distribution**: Artifacts are automatically uploaded and available for download
5. **Version Control**: All build configurations are version-controlled with your code
6. **Free Tier**: GitHub provides free minutes for public repositories

## Security Considerations

1. **Signing Secrets**: Never commit keystore files or passwords to the repository
2. **Environment Variables**: Use GitHub Secrets for sensitive information
3. **Artifact Access**: Only repository collaborators can download artifacts by default
4. **Workflow Permissions**: Review and limit workflow permissions as needed

For more information about GitHub Actions, see the [GitHub Actions documentation](https://docs.github.com/en/actions).