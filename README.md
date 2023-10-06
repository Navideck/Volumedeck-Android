# Volumedeck SDK - Android

## Overview

<p align="center">
  <img src="https://navideck.com/sites/navideck.com/files/2023-10/Volumedeck%20SDK.png" height=150 />
</p>

Volumedeck provides automatic volume adjustment based on GPS speed, improving the media-listening experience for users in vehicles and public transport.

## Key Features

- Speed-Sensitive Volume Adjustment: Automatically adjusts audio volume based on the vehicle's speed for consistent audio levels.
- Enhanced Safety and Focus: Eliminates the need for manual volume adjustments, enhancing driver safety and passenger convenience.
- Efficient and Reliable: Real-time speed-based volume control for smooth and uninterrupted listening experiences.
- Easy Integration: User-friendly API and comprehensive documentation for straightforward implementation.
- Versatile Applications: Enhances navigation, music streaming, and audio content delivery apps for various vehicles and public transport.
- Seamless integration with hardware volume keys for unified volume control in tandem with Volumedeck adjustments.
- Easy integration with UniversalVolume for unified volume control.
- Does not require internet connectivity
- Jetpack Compose / XML layout / Kotlin & Java support

## Getting Started with Volumedeck

To use Volumedeck SDK, follow these steps:

### Add Volumedeck SDK to your project

Volumedeck SDK can be easily added to your Android project using Jitpack. Jitpack is a package repository service that allows you to use Git repositories as dependencies in your projects.

To add Volumedeck SDK to your project, follow these steps:

1. Open your project's `build.gradle` file.

2. Add the Jitpack repository to the list of repositories:

```groovy
allprojects {
    repositories {
        // ... other repositories ...
        maven { url 'https://jitpack.io' }
    }
}
```

3. Open your app module's `build.gradle` file.

4. Add the Volumedeck SDK dependency:

```groovy
dependencies {
    implementation 'com.github.Navideck:Volumedeck-Android:1.5.0'
}
```

Replace `'1.5.0'` with the latest release version of Volumedeck SDK. You can find the latest version on the [Volumedeck SDK GitHub releases page](https://github.com/Navideck/Volumedeck-Android/releases).

5. Sync your project with Gradle by clicking on "Sync Now" in Android Studio.

Now, Volumedeck SDK is successfully added to your project via Jitpack. You can start using the Volumedeck API in your app as described in the previous sections.

Please note that Jitpack fetches the library directly from the GitHub repository, so you need an active internet connection while building your project. Also, ensure that you are using a version that is compatible with your app's requirements.

### Import Volumedeck

```kotlin
import com.navideck.Volumedeck_android.Volumedeck
```

### Initialize Volumedeck

Initialize the Volumedeck SDK in your activity's `onCreate` method:

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var volumeDeck: Volumedeck

    override fun onCreate(savedInstanceState: Bundle?) {
        // ....

        // Initialize Volumedeck SDK
        volumeDeck = Volumedeck(
            activity = this,
            autoStart = true, // Set to false if you don't want to start volumedeck on initialization
            runInBackground = true, // Set to false if you don't want to run in the background
            showStopButtonInNotification = true, // Show stop button in the notification when running in the background
            showSpeedAndVolumeChangesInNotification = true, // Show speed and volume changes in the notification when running in the background
            locationServicesStatusChange = { status: Boolean ->
                // Get Location status on/off
            },
            onLocationUpdate = { speed: Float, volume: Float ->
                // Get updates of speed and volume changes
            }
        )

        // To Start Volumedeck SDK
        volumeDeck.start(this)

        // To stop Volumedeck SDK
        // Call this when you want to stop using Volumedeck SDK (e.g., in onDestroy method)
        volumeDeck.stop(this)
    }

    // If `runInBackground` is true, stop Volumedeck SDK after killing the app using onDestroy
    override fun onDestroy() {
        volumeDeck.stop(activity)
        super.onDestroy()
    }
}
```

## Permissions

To use the Volumedeck SDK in your Android app, you need to add the following permissions to your AndroidManifest.xml file:

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

These permissions are necessary for the Volumedeck SDK to access the device's location information. The SDK utilizes the device's speed information to adjust the audio volume based on the vehicle's speed.

Please note that developers must handle these permissions on their own. The Volumedeck SDK does not handle permission requests directly. It is the responsibility of the app developer to request and handle these permissions in their application code.

### Running in Background

If you want to run Volumedeck SDK in the background, make sure to add the following service declaration to your AndroidManifest.xml file:

```xml
<application
    ......
    <service
        android:name="com.navideck.volumedeck_android.VolumedeckBackgroundService"
        android:foregroundServiceType="location"
        android:enabled="true"
        android:exported="true" />
</application>
```

You'll also need to add the following permissions:

```xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" /> <!-- Needed for Android 14+ -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />    <!-- Needed for Android 12+ -->
```

The `FOREGROUND_SERVICE` permission allows the SDK to run as a foreground service, enabling continuous operation even when the app is in the background. The `POST_NOTIFICATIONS` permission is required on Android 12+ to show notifications with speed and volume changes when running the SDK in the background.

Please make sure to add these permissions to your AndroidManifest.xml file to ensure smooth functioning of the Volumedeck SDK in your Android app.

## Java

Volumedeck is fully compatible with `Java`. When using Java you can initialize Volumedeck using:

```java
Volumedeck volumedeck = new Volumedeck(context);
```

## UniversalVolume - Unified Volume Control

An Android library for easy volume control on different devices. Integrates smoothly with Volumedeck for intuitive volume adjustment across all devices.

You can use [UniversalVolume](https://github.com/Navideck/Universal-Volume) with Volumedeck. Simply import UniversalVolume, and Volumedeck will automatically use UniversalVolume for changing volume.

## Free to Use
Volumedeck SDK is free to use, providing you with the full functionality of the SDK without any time limitations. You are welcome to integrate it into both personal and commercial projects. When using Volumedeck SDK for free, a watermark will be presented during runtime. It is strictly prohibited to hide, remove, or alter in any way the watermark from the free version of Volumedeck SDK.

### Activation Key and Watermark Removal
For those who wish to remove the watermark from their app, we offer an activation key that allows you to use the SDK without any watermarks. However, please be aware that the watermark-free version is not available for free and requires a purchase.

To inquire about purchasing an activation key or if you have any other questions related to licensing and usage, please contact us at team@navideck.com. We will be happy to assist you with the process and provide you with the necessary information.

## Contact

For questions or support, please contact us at team@navideck.com. Thank you for choosing Volumedeck SDK!