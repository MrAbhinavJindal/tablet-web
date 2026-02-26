@echo off
echo Initializing Git repository...
git init

echo Adding all files...
git add .

echo Creating initial commit...
git commit -m "Initial commit: Android app with dashboard frontend"

echo.
echo Next steps:
echo 1. Create a new repository on GitHub named 'tablet-web'
echo 2. Run these commands:
echo.
echo    git remote add origin https://github.com/MrAbhinavJindal/tablet-web.git
echo    git branch -M main
echo    git push -u origin main
echo.
echo 3. Go to GitHub Actions to download the built APK
echo.
pause
