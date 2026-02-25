@echo off
set ADB=platform-tools\adb.exe

echo Setting up ADB reverse port forwarding...
%ADB% reverse tcp:8000 tcp:8000

echo.
echo Done! Tablet can now access backend at: http://localhost:8000
echo.
echo Keep tablet connected via USB while using the app.
pause
