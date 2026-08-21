package com.ceps.easyphone;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(30, 40, 30, 30);
        main.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("CEPS EASY PHONE");
        title.setTextSize(32);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setPadding(10, 20, 10, 30);
        main.addView(title);

        addButton(main, "☎  PHONE", Color.rgb(40, 120, 220), v ->
                openIntent(new Intent(Intent.ACTION_DIAL)));

        addButton(main, "💬  WHATSAPP", Color.rgb(30, 160, 70), v ->
                openPackage("com.whatsapp"));

        addButton(main, "✉  MESSAGES", Color.rgb(70, 130, 220), v ->
                openIntent(new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"))));

        addButton(main, "📷  CAMERA", Color.rgb(80, 80, 80), v ->
                openIntent(new Intent("android.media.action.IMAGE_CAPTURE")));

        addButton(main, "👤  CONTACTS", Color.rgb(240, 150, 20), v ->
                openIntent(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("content://contacts/people/"))));

        addButton(main, "⚙  SETTINGS", Color.rgb(100, 100, 100), v ->
                openIntent(new Intent(Settings.ACTION_SETTINGS)));

        setContentView(main);
    }

    private void addButton(LinearLayout layout, String text, int color,
                           android.view.View.OnClickListener listener) {

        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(22);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(color);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setPadding(15, 20, 15, 20);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 125);

        params.setMargins(0, 10, 0, 10);
        button.setLayoutParams(params);
        button.setOnClickListener(listener);

        layout.addView(button);
    }

    private void openPackage(String packageName) {
        Intent intent = getPackageManager()
                .getLaunchIntentForPackage(packageName);

        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this,
                    "This app is not installed",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void openIntent(Intent intent) {
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this,
                    "Unable to open this app",
                    Toast.LENGTH_LONG).show();
        }
    }
}
