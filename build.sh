#!/bin/bash

echo "Building Barcode Scanner Android App..."
echo

echo "Cleaning previous builds..."
./gradlew clean

echo
echo "Running tests..."
./gradlew test

echo
echo "Building debug APK..."
./gradlew assembleDebug

echo
echo "Build completed!"
echo "Debug APK location: app/build/outputs/apk/debug/app-debug.apk"
echo
