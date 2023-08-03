# Volumedeck SDK - Android

## Overview

Volumedeck SDK provides speed-based audio volume control for Android apps, improving the audio experience for users in vehicles and public transport.

## Key Features

- Speed-Sensitive Volume Adjustment: Automatically adjusts audio volume based on the vehicle's speed for consistent audio levels.
- Enhanced Safety and Focus: Eliminates the need for manual volume adjustments, enhancing driver safety and passenger convenience.
- Efficient and Reliable: Real-time speed-based volume control for smooth and uninterrupted listening experiences.
- Easy Integration: User-friendly API and comprehensive documentation for straightforward implementation.
- Versatile Applications: Enhances navigation, music streaming, and audio content delivery apps for various vehicles and public transport.
- Seamless integration with hardware volume keys for unified volume control in tandem with Gesturedeck adjustments.
- Easy integration with UniversalVolume for unified volume control.

## Getting Started

To use Volumedeck SDK, follow these steps:

1. Import the Volumedeck SDK package into your Kotlin class:

```kotlin
import com.navideck.volumedeck.Volumedeck
```

2. Initialize Volumedeck SDK in your activity's `onCreate` method:

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var volumeDeck: Volumedeck

    override fun onCreate(savedInstanceState: Bundle?) {
        // ....

        // Initialize Volumedeck SDK
        // Make sure to ask Location permission, and Notification permission (if running on Android 12+)
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
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />.    <!-- Needed for Android 12+ -->
```

The `FOREGROUND_SERVICE` permission allows the SDK to run as a foreground service, enabling continuous operation even when the app is in the background. The `POST_NOTIFICATIONS` permission is required on Android 12+ to show notifications with speed and volume changes when running the SDK in the background.

Please make sure to add these permissions to your AndroidManifest.xml file to ensure smooth functioning of the Volumedeck SDK in your Android app.

## UniversalVolume - Unified Volume Control

An Android library for easy volume control on different devices. Integrates smoothly with Volumedeck for intuitive volume adjustments using pan gestures.

You can use [UniversalVolume](https://github.com/Navideck/Universal-Volume) with Volumedeck. Simply import UniversalVolume, and Volumedeck will automatically use UniversalVolume for changing volume.

## Contact

For questions or support, please contact us at team@navideck.com. Thank you for choosing Volumedeck SDK!