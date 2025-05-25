@echo off
echo Building Barcode Scanner Android App...
echo.

echo Cleaning previous builds...
call gradlew clean

echo.
echo Running tests...
call gradlew test

echo.
echo Building debug APK...
call gradlew assembleDebug

echo.
echo Build completed!
echo Debug APK location: app\build\outputs\apk\debug\app-debug.apk
echo.

pause
