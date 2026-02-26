# Setup Instructions for tablet-web Repository

## Step 1: Initialize Git Repository

```bash
cd tablet-web
git init
git add .
git commit -m "Initial commit: Android app with dashboard frontend"
```

## Step 2: Create GitHub Repository

1. Go to https://github.com/new
2. Repository name: `tablet-web`
3. Description: "Android app for tablet dashboard monitor"
4. Public or Private: Choose as needed
5. DO NOT initialize with README (we already have one)
6. Click "Create repository"

## Step 3: Push to GitHub

```bash
git remote add origin https://github.com/MrAbhinavJindal/tablet-web.git
git branch -M main
git push -u origin main
```

## Step 4: Verify GitHub Actions

1. Go to https://github.com/MrAbhinavJindal/tablet-web/actions
2. The build workflow should trigger automatically
3. Wait for the build to complete (takes ~5-10 minutes)
4. Download the APK from the artifacts section

## Step 5: Configure Backend URL

Before building, update the backend URL in:
`app/src/main/assets/frontend/config.js`

Change the IP address to your laptop's WiFi IP:
```javascript
const API_BASE_URL = 'http://YOUR_LAPTOP_IP:8000';
```

Then commit and push the change to trigger a new build.

## Step 6: Install APK on Tablet

1. Download APK from GitHub Actions artifacts
2. Transfer to tablet via USB/email/cloud
3. Enable "Install from unknown sources" in tablet settings
4. Install the APK
5. Open the app - it will display in portrait mode

## Notes

- The app is locked to portrait orientation
- WebView loads the local HTML files from assets
- Make sure tablet and laptop are on the same WiFi network
- Backend must be running with: `uvicorn main:app --host 0.0.0.0 --port 8000`

## Troubleshooting

If build fails:
1. Check GitHub Actions logs for errors
2. Ensure all files are committed
3. Verify gradle wrapper has execute permissions (Linux/Mac)

If app doesn't connect to backend:
1. Verify laptop IP address with `ipconfig` (Windows) or `ifconfig` (Linux/Mac)
2. Check firewall allows port 8000
3. Ensure both devices on same WiFi network
