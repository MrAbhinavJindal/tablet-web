# Command Reference - tablet-web

## Quick Commands

### Initialize and Push to GitHub (All-in-One)
```bash
cd "d:\D_Drive\Automation Full\tablet-web"
git init
git add .
git commit -m "Initial commit: Android app with dashboard frontend"
git remote add origin https://github.com/MrAbhinavJindal/tablet-web.git
git branch -M main
git push -u origin main
```

### Or Use the Helper Script
```bash
cd "d:\D_Drive\Automation Full\tablet-web"
.\init-repo.bat
# Then follow the on-screen instructions
```

## Update Backend URL

### Edit config.js
```bash
notepad app\src\main\assets\frontend\config.js
```

Change:
```javascript
const API_BASE_URL = 'http://192.168.96.1:8000';
```

To your laptop's IP:
```javascript
const API_BASE_URL = 'http://YOUR_IP:8000';
```

### Commit and Push Update
```bash
git add app\src\main\assets\frontend\config.js
git commit -m "Update backend URL to correct IP"
git push
```

## Local Build (Optional)

### Build APK Locally
```bash
.\gradlew assembleRelease
```

### Find APK
```
app\build\outputs\apk\release\app-release-unsigned.apk
```

## GitHub Actions

### View Builds
```
https://github.com/MrAbhinavJindal/tablet-web/actions
```

### Download APK
1. Click on latest workflow run
2. Scroll to "Artifacts" section
3. Download "app-release"
4. Extract ZIP to get APK

## Install on Tablet

### Via USB
```bash
adb install app-release-unsigned.apk
```

### Via File Transfer
1. Copy APK to tablet
2. Open file manager on tablet
3. Tap APK file
4. Allow "Install from unknown sources"
5. Tap "Install"

## Backend Setup

### Start Backend
```bash
cd "d:\D_Drive\Automation Full\appsmith_fastapi_dashboard\backend"
uvicorn main:app --host 0.0.0.0 --port 8000
```

### Check Laptop IP
```bash
ipconfig
# Look for "Wireless LAN adapter Wi-Fi" → "IPv4 Address"
```

## Verify Connection

### Test from Tablet Browser
```
http://YOUR_LAPTOP_IP:8000/docs
```

Should show FastAPI documentation page.

## Common Issues

### "Cannot connect to backend"
```bash
# 1. Check laptop IP
ipconfig

# 2. Update config.js with correct IP
notepad app\src\main\assets\frontend\config.js

# 3. Rebuild and reinstall APK
git add .
git commit -m "Fix backend URL"
git push
```

### "Build failed on GitHub"
```bash
# Check logs at:
https://github.com/MrAbhinavJindal/tablet-web/actions

# Common fixes:
# - Ensure all files are committed
# - Check build.yml syntax
# - Verify gradle files are correct
```

### "APK won't install"
```
Settings → Security → Unknown Sources → Enable
```

## File Locations

### Frontend Assets
```
app\src\main\assets\frontend\
├── config.js
├── main.html
├── paint.html
└── icons\
```

### Android Code
```
app\src\main\java\com\abhinavjindal\tabletweb\MainActivity.java
```

### Build Configuration
```
app\build.gradle
build.gradle
settings.gradle
```

### GitHub Actions
```
.github\workflows\build.yml
```

## Useful Links

- **GitHub Repo**: https://github.com/MrAbhinavJindal/tablet-web
- **GitHub Actions**: https://github.com/MrAbhinavJindal/tablet-web/actions
- **Create New Repo**: https://github.com/new
- **Android Developer**: https://developer.android.com/

## Project Info

- **Package**: com.abhinavjindal.tabletweb
- **App Name**: Tablet Monitor
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 33 (Android 13)
- **Orientation**: Portrait only
- **Permissions**: INTERNET, WAKE_LOCK

## Quick Test

After installing APK:
1. Open app on tablet
2. Should see dashboard in portrait mode
3. Check if clocks are updating
4. Verify weather/battery/attendance load
5. Test paint button
6. Test volume slider and mic button

If data shows "--" or "Loading...", check backend connection.
