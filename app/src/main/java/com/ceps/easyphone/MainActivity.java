package com.ceps.easyphone;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.provider.Settings;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout screen = new LinearLayout(this);
        screen.setOrientation(LinearLayout.VERTICAL);
        screen.setPadding(40, 60, 40, 40);
        screen.setGravity(Gravity.CENTER_HORIZONTAL);
        screen.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("CEPS Easy Phone");
        title.setTextSize(32);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 20, 0, 40);

        screen.addView(title);

        Button wifiButton = new Button(this);
        wifiButton.setText("WI-FI");
        wifiButton.setTextSize(22);
        wifiButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
        });

        screen.addView(wifiButton);

        Button bluetoothButton = new Button(this);
        bluetoothButton.setText("BLUETOOTH");
        bluetoothButton.setTextSize(22);
        bluetoothButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivity(intent);
        });

        screen.addView(bluetoothButton);

        Button settingsButton = new Button(this);
        settingsButton.setText("PHONE SETTINGS");
        settingsButton.setTextSize(22);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);
        });

        screen.addView(settingsButton);

        setContentView(screen);
    }
}
