# Automotive training - team2 CarApp 

## How to deploy our app as a system application

### init setup
1. run `emulator -avd Automotive_API_33 -writable-system -no-snapshot-load` command to launch the emulator with writable file system.
> NOTE: put the Android SDK emulator command to the environment variable
> Or: run this `~/Library/Android/sdk/emulator/emulator -avd Automotive_API_33 -writable-system -no-snapshot-load`

2. run `adb root` and `adb remount` commands to remount the emulator file system
> You should see `remount succeeded` after execute `adb remount` and a `#` symbol when you input `adb shell`

3. run `adb shell mkdir /system/app/CarApp-team2` to create the app directory

### during development
run the `deploy-system-app.sh` shell script

### Troubleshooting
_please add here if you meet something else_

- Q: If you were told that `apksigner: command not found`
- A:  Add a absolute path before your `apksigner` command, for me it likes:
`~/Library/Android/sdk/build-tools/30.0.3/apksigner sign --key platform.pk8 --cert platform.x509.pem app/build/outputs/apk/dev/debug/app-dev-debug.apk`
        

## Docs
[WIKI](https://github.com/TW-Smart-CoE/ARK-WIKI)

