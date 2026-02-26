@echo off
echo Pushing to GitHub to trigger build...
git add .
git commit -m "Trigger APK build"
git push

echo.
echo Build triggered! Check status at:
echo https://github.com/MrAbhinavJindal/tablet-web/actions
echo.
pause
