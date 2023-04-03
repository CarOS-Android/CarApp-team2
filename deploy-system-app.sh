#! /bin/bash

# 1. build the apk
./gradlew :app:assembleDevDebug

# 2. sign the app the system signature
apksigner sign --key platform.pk8 --cert platform.x509.pem app/build/outputs/apk/dev/debug/app-dev-debug.apk

# 3. remount the emulator file system
adb root
adb remount

# 4. push the apk file to system/app/ directory
adb push app/build/outputs/apk/dev/debug/app-dev-debug.apk /system/app/CarApp-team2

# 5. reboot the emulator
adb reboot