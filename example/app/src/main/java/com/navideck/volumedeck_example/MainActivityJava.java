package com.navideck.volumedeck_example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.navideck.VolumedeckExample.R;
import com.navideck.volumedeck_android.Volumedeck;

public class MainActivityJava extends AppCompatActivity {
    private Volumedeck volumedeck;
    private Button btnStart;
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setTitle("Volumedeck Example Java");

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        setupVolumedeck();

        btnStart.setOnClickListener(v -> volumedeck.start());
        btnStop.setOnClickListener(v -> volumedeck.stop());
    }

    @SuppressLint("SetTextI18n")
    private void setupVolumedeck() {
        if (!AppUtils.hasValidPermission(this)) return;
        TextView volumedeckEvent = findViewById(R.id.txtEvent);
        TextView txtGpsError = findViewById(R.id.txtGpsError);

        volumedeck = new Volumedeck(
                this,
                // autoStart
                true,
                // locationServicesStatusChange
                (Boolean isOn) -> {
                    txtGpsError.setVisibility(isOn ? View.GONE : View.VISIBLE);
                    return null;
                },
                // onLocationUpdate
                (Float speed, Float volume) -> {
                    volumedeckEvent.setText("Speed: " + speed + " Volume: " + volume);
                    return null;
                },
                // onStart
                () -> {
                    updateButtonsState(true);
                    return null;
                },
                // onStop
                () -> {
                    updateButtonsState(false);
                    return null;
                }
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtils.getPermissionCode() && grantResults.length > 0) {
            setupVolumedeck();
        }
    }

    private void updateButtonsState(Boolean isEnabled) {
        btnStart.setEnabled(!isEnabled);
        btnStop.setEnabled(isEnabled);
    }
}