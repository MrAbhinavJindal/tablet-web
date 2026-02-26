@echo off
echo Building APK locally...
call gradlew.bat assembleDebug

if exist app\build\outputs\apk\debug\app-debug.apk (
    echo.
    echo Build successful!
    copy /Y app\build\outputs\apk\debug\app-debug.apk app-debug.apk
    echo APK copied to: app-debug.apk
) else (
    echo.
    echo Build failed!
)

echo.
pause
