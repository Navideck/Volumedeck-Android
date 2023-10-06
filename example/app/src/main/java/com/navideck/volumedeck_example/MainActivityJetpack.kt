package com.navideck.volumedeck_example

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.navideck.VolumedeckExample.R
import com.navideck.volumedeck_android.Volumedeck

class MainActivityJetpack : AppCompatActivity() {
    private var volumedeck: Volumedeck? = null
    private val volumedeckEvent = mutableStateOf("")
    private val isVolumedeckStarted = mutableStateOf(false)
    private val showGpsError = mutableStateOf(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Volumedeck Example Jetpack"

        setupVolumedeck()

        setContent {
            val systemColorScheme =
                if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

            MaterialTheme(
                colorScheme = systemColorScheme.copy(
                    primary = Color(ContextCompat.getColor(this, R.color.colorPrimary))
                ),
            ) {
                Surface {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            Button(
                                enabled = !isVolumedeckStarted.value,
                                onClick = {
                                    volumedeck?.start()
                                }) {
                                Text(text = "Start")
                            }
                            Button(
                                enabled = isVolumedeckStarted.value,
                                onClick = {
                                    volumedeck?.stop()
                                }) {
                                Text(text = "Stop")
                            }
                        }
                        Divider()
                        if (showGpsError.value)
                            Text(text = "Please turn on GPS", color = Color.Red)
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = volumedeckEvent.value.ifEmpty { "Volumedeck Event" },
                                modifier = Modifier.padding(8.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W400
                            )
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupVolumedeck() {
        if (!AppUtils.hasValidPermission(this)) return

        volumedeck = Volumedeck(
            activity = this,
            locationServicesStatusChange = { isOn: Boolean ->
                showGpsError.value = !isOn
            },
            onLocationUpdate = { speed: Float, volume: Float ->
                volumedeckEvent.value = "Speed: $speed, Volume: $volume"
            },
            onStart = {
                isVolumedeckStarted.value = true
            },
            onStop = {
                isVolumedeckStarted.value = false
            },
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == AppUtils.permissionCode && grantResults.isNotEmpty()) setupVolumedeck()
    }


}