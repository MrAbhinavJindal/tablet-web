# Tablet Web Monitor

Android app for displaying dashboard on tablet in portrait mode.

## Features
- Portrait mode display
- WebView-based frontend
- World clocks (India, Switzerland, UK)
- Weather, battery, attendance info
- Calendar events
- Volume control with mic toggle
- Paint functionality

## Build

### Local Build
```bash
./gradlew assembleRelease
```

### GitHub Actions
Push to main branch to trigger automatic APK build. Download from Actions artifacts.

## Installation
1. Download APK from GitHub Actions artifacts
2. Enable "Install from unknown sources" on Android device
3. Install APK
4. Update `config.js` with your backend API URL

## Configuration
Edit `app/src/main/assets/frontend/config.js` to set your backend URL:
```javascript
const API_BASE_URL = 'http://YOUR_LAPTOP_IP:8000';
```
