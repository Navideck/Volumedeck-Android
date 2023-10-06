package com.navideck.volumedeck_example

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object AppUtils {
    @JvmStatic
    val permissionCode = 852915

    @JvmStatic
    fun hasValidPermission(activity: Activity): Boolean {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        var hasPermission = true
        for (permission in permissions) {
            val permissionResult = ContextCompat.checkSelfPermission(activity, permission)
            hasPermission = permissionResult == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) break
        }

        if (hasPermission) return true

        ActivityCompat.requestPermissions(
            activity,
            permissions,
            permissionCode
        )
        return false
    }

}
