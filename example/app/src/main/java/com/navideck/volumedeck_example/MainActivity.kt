package com.navideck.volumedeck_example

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.navideck.VolumedeckExample.R
import com.navideck.volumedeck_android.Volumedeck

class MainActivity : AppCompatActivity() {
    private var volumedeck: Volumedeck? = null
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)

        setupVolumedeck()

        btnStart.setOnClickListener {
            volumedeck?.start()
        }

        btnStop.setOnClickListener {
            volumedeck?.stop()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupVolumedeck() {
        if (!AppUtils.hasValidPermission(this)) return
        val volumedeckEvent = findViewById<TextView>(R.id.txtEvent)
        val txtGpsError = findViewById<TextView>(R.id.txtGpsError)

        volumedeck = Volumedeck(
            activity = this,
            locationServicesStatusChange = { isOn: Boolean ->
                txtGpsError.visibility = if (isOn) TextView.GONE else TextView.VISIBLE
            },
            onLocationUpdate = { speed: Float, volume: Float ->
                volumedeckEvent.text = "Speed: $speed, Volume: $volume"
            },
            onStart = {
                updateButtonsState(true)
            },
            onStop = {
                updateButtonsState(false)
            },
        )

        // Set mock speed for testing
        // volumedeck?.setMockSpeed(this, 30f)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppUtils.permissionCode && grantResults.isNotEmpty()) setupVolumedeck()
    }

    private fun updateButtonsState(isEnabled: Boolean) {
        btnStart.isEnabled = !isEnabled
        btnStop.isEnabled = isEnabled
    }
}