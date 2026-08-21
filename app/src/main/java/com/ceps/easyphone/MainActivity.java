package com.ceps.easyphone;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout screen = new LinearLayout(this);
        screen.setOrientation(LinearLayout.VERTICAL);
        screen.setGravity(Gravity.CENTER_HORIZONTAL);
        screen.setPadding(30, 40, 30, 30);
        screen.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("CEPS Easy Phone");
        title.setTextSize(32);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        screen.addView(title);

        addButton(screen, "☎ PHONE", new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL));
            }
        });

        addButton(screen, "💬 WHATSAPP", new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = getPackageManager()
                        .getLaunchIntentForPackage("com.whatsapp");
                if (intent != null) startActivity(intent);
            }
        });

        addButton(screen, "📷 CAMERA", new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });

        addButton(screen, "📶 WI-FI", new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

        addButton(screen, "🔵 BLUETOOTH", new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
            }
        });

        addButton(screen, "⚙ PHONE SETTINGS", new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        setContentView(screen);
    }

    private void addButton(LinearLayout layout, String text,
                           View.OnClickListener listener) {

        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(24);
        button.setAllCaps(false);
        button.setOnClickListener(listener);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        110);

        params.setMargins(0, 12, 0, 12);
        layout.addView(button, params);
    }
}
