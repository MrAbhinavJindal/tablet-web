@echo off
set ADB=platform-tools\adb.exe

echo Uninstalling old app...
%ADB% uninstall com.abhinavjindal.tabletweb 2>nul

echo Installing APK from GitHub...
%ADB% install app-debug.apk

echo.
echo Done! App installed on device.
pause
