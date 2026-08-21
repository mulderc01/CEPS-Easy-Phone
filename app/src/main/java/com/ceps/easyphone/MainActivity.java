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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(Color.WHITE);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(dp(20), dp(20), dp(20), dp(25));
        main.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("CEPS EASY PHONE");
        title.setTextSize(30);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, dp(10), 0, dp(20));

        main.addView(title);

        addButton(main, "☎  PHONE", Color.rgb(30, 115, 230), v ->
                openIntent(new Intent(Intent.ACTION_DIAL)));

        addButton(main, "💬  WHATSAPP", Color.rgb(25, 175, 70), v ->
                openWhatsApp());

        addButton(main, "✉  MESSAGES", Color.rgb(45, 125, 220), v ->
                openIntent(new Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse("smsto:")
                )));

        addButton(main, "📷  CAMERA", Color.rgb(80, 80, 80), v ->
                openIntent(new Intent(
                        "android.media.action.IMAGE_CAPTURE"
                )));

        addButton(main, "👤  CONTACTS", Color.rgb(245, 155, 15), v ->
                openIntent(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("content://contacts/people/")
                )));

        addButton(main, "⚙  SETTINGS", Color.rgb(90, 90, 90), v ->
                openIntent(new Intent(
                        Settings.ACTION_SETTINGS
                )));

        scrollView.addView(main);
        setContentView(scrollView);
    }

    private void addButton(
            LinearLayout layout,
            String text,
            int color,
            View.OnClickListener listener) {

        Button button = new Button(this);

        button.setText(text);
        button.setTextSize(22);
        button.setTextColor(Color.WHITE);
        button.setBackgroundColor(color);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setIncludeFontPadding(true);
        button.setPadding(
                dp(10),
                dp(8),
                dp(10),
                dp(8)
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(76)
                );

        params.setMargins(
                0,
                dp(5),
                0,
                dp(5)
        );

        button.setLayoutParams(params);
        button.setOnClickListener(listener);

        layout.addView(button);
    }

    private void openWhatsApp() {
        try {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("whatsapp://send")
            );
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Unable to open WhatsApp",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void openIntent(Intent intent) {
        try {
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Unable to open this app",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private int dp(int value) {
        float density =
                getResources()
                        .getDisplayMetrics()
                        .density;

        return (int) (value * density + 0.5f);
    }
}
