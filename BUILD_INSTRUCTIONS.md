# Build Instructions for Barcode Scanner Android App

## Prerequisites

### Required Software
1. **Android Studio** (Arctic Fox or later)
   - Download from: https://developer.android.com/studio
   - Install with default settings

2. **Java Development Kit (JDK) 11 or later**
   - Android Studio includes OpenJDK
   - Or install Oracle JDK/OpenJDK separately

3. **Android SDK**
   - Minimum SDK: API 24 (Android 7.0)
   - Target SDK: API 34 (Android 14)
   - Compile SDK: API 34

### Hardware Requirements
- **For Development**: 8GB RAM minimum, 16GB recommended
- **For Testing**: Android device with API 24+ or emulator
- **Camera**: Physical device recommended for barcode scanning testing

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd Barcode-Scan-Android
```

### 2. Configure Android Studio
1. Open Android Studio
2. Select "Open an existing Android Studio project"
3. Navigate to the cloned directory and select it
4. Wait for Gradle sync to complete

### 3. Configure SDK Path
1. Update `local.properties` file with your SDK path:
```properties
sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
```

### 4. Sync Dependencies
1. Click "Sync Now" when prompted
2. Or go to File → Sync Project with Gradle Files

## Building the App

### Debug Build
```bash
# Command line
./gradlew assembleDebug

# Or in Android Studio
Build → Make Project (Ctrl+F9)
```

### Release Build
```bash
# Command line
./gradlew assembleRelease

# Or in Android Studio
Build → Generate Signed Bundle/APK
```

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# All tests
./gradlew check
```

## Installation

### Install on Device
```bash
# Debug APK
./gradlew installDebug

# Release APK
./gradlew installRelease
```

### Manual Installation
1. Build APK using above commands
2. APK location: `app/build/outputs/apk/debug/app-debug.apk`
3. Transfer to device and install
4. Enable "Install from unknown sources" if needed

## Troubleshooting

### Common Issues

#### 1. Gradle Sync Failed
- **Solution**: Check internet connection, update Gradle version
- **Command**: `./gradlew --refresh-dependencies`

#### 2. SDK Not Found
- **Solution**: Update `local.properties` with correct SDK path
- **Check**: Android Studio → File → Project Structure → SDK Location

#### 3. Build Tools Version
- **Solution**: Update build tools in `app/build.gradle`
- **Check**: SDK Manager → SDK Tools → Android SDK Build-Tools

#### 4. Memory Issues
- **Solution**: Increase heap size in `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
```

#### 5. Camera Permission Issues
- **Solution**: Test on physical device, not emulator
- **Check**: Device settings → Apps → Permissions

### Performance Optimization

#### For Low-End Devices
1. Enable R8/ProGuard in release builds
2. Use APK splits for different architectures
3. Optimize image resources

#### For Development
1. Use incremental builds
2. Enable parallel builds in `gradle.properties`:
```properties
org.gradle.parallel=true
org.gradle.configureondemand=true
```

## Testing

### Device Testing
1. **Minimum Requirements**:
   - Android 7.0 (API 24)
   - Camera with autofocus
   - 2GB RAM minimum

2. **Recommended Testing**:
   - Various screen sizes
   - Different Android versions
   - Low-end and high-end devices

### Barcode Testing
1. Test with various barcode formats:
   - EAN-13, UPC-A, QR codes
   - Code 128, Code 39, etc.

2. Test scanning conditions:
   - Different lighting
   - Various distances
   - Damaged/poor quality barcodes

### Excel Export Testing
1. Test with different data sizes
2. Verify file format compatibility
3. Test sharing functionality

## Deployment

### Play Store Preparation
1. Generate signed release APK
2. Test thoroughly on multiple devices
3. Prepare store listing materials
4. Follow Play Store guidelines

### Direct Distribution
1. Build release APK
2. Sign with release keystore
3. Test installation on target devices
4. Distribute via preferred method

## Development Tips

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable names
- Add documentation for public APIs
- Keep functions small and focused

### Architecture
- Maintain Clean Architecture principles
- Use dependency injection properly
- Keep UI logic in ViewModels
- Separate business logic in Use Cases

### Performance
- Use background threads for heavy operations
- Optimize database queries
- Handle memory efficiently
- Test on low-end devices

## Support

### Getting Help
1. Check existing GitHub issues
2. Review Android documentation
3. Check Stack Overflow for common issues
4. Contact development team

### Reporting Issues
1. Include device information
2. Provide steps to reproduce
3. Include relevant logs
4. Specify expected vs actual behavior

## Version Information

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Kotlin Version**: 1.9.10
- **Gradle Version**: 8.2
- **Android Gradle Plugin**: 8.2.0
