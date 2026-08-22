package com.ceps.easyphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.provider.AlarmClock;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private LinearLayout main;
    private SharedPreferences prefs;

    private final String[] appNames = {
            "Phone",
            "WhatsApp",
            "Messages",
            "Camera",
            "Contacts",
            "Gallery",
            "Google",
            "Alarm",
            "Calendar",
            "Calculator",
            "Facebook",
            "Settings"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("easy_phone_apps", MODE_PRIVATE);
        buildScreen();
    }

    private void buildScreen() {

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(Color.WHITE);

        main = new LinearLayout(this);
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

        if (isVisible("Phone"))
            addButton(main, "☎  PHONE", Color.rgb(30, 115, 230), v ->
                    openIntent(new Intent(Intent.ACTION_DIAL)));

        if (isVisible("WhatsApp"))
            addButton(main, "💬  WHATSAPP", Color.rgb(25, 175, 70), v ->
                    openWhatsApp());

        if (isVisible("Messages"))
            addButton(main, "✉  MESSAGES", Color.rgb(45, 125, 220), v ->
                    openIntent(new Intent(
                            Intent.ACTION_SENDTO,
                            Uri.parse("smsto:")
                    )));

        if (isVisible("Camera"))
            addButton(main, "📷  CAMERA", Color.rgb(80, 80, 80), v ->
                    openIntent(new Intent(
                            "android.media.action.IMAGE_CAPTURE"
                    )));

        if (isVisible("Contacts"))
            addButton(main, "👤  CONTACTS", Color.rgb(245, 155, 15), v ->
                    openIntent(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("content://contacts/people/")
                    )));

        if (isVisible("Gallery"))
            addButton(main, "🖼  GALLERY", Color.rgb(125, 75, 190), v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("image/*");
                openIntent(intent);
            });

        if (isVisible("Google"))
            addButton(main, "🌐  GOOGLE", Color.rgb(45, 125, 220), v ->
                    openIntent(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com")
                    )));

        if (isVisible("Alarm"))
            addButton(main, "⏰  ALARM", Color.rgb(220, 80, 80), v ->
                    openIntent(new Intent(AlarmClock.ACTION_SHOW_ALARMS)));

        if (isVisible("Calendar"))
            addButton(main, "📅  CALENDAR", Color.rgb(45, 150, 100), v ->
                    openIntent(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("content://com.android.calendar/time/")
                    )));

        if (isVisible("Calculator"))
            addButton(main, "🧮  CALCULATOR", Color.rgb(70, 110, 160), v -> {
                try {
                    Intent intent = Intent.makeMainSelectorActivity(
                            Intent.ACTION_MAIN,
                            Intent.CATEGORY_APP_CALCULATOR
                    );
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(
                            this,
                            "Unable to open Calculator",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

        if (isVisible("Facebook"))
            addButton(main, "👍  FACEBOOK", Color.rgb(50, 90, 180), v ->
                    openFacebook());

        if (isVisible("Settings"))
            addButton(main, "⚙  SETTINGS", Color.rgb(90, 90, 90), v ->
                    openIntent(new Intent(Settings.ACTION_SETTINGS)));

        addButton(main, "☑  CHOOSE APPS", Color.rgb(20, 140, 140), v ->
                showAppChooser());

        scrollView.addView(main);
        setContentView(scrollView);
    }

    private boolean isVisible(String name) {
        return prefs.getBoolean(name, true);
    }

    private void showAppChooser() {

        boolean[] checked = new boolean[appNames.length];

        for (int i = 0; i < appNames.length; i++) {
            checked[i] = isVisible(appNames[i]);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose apps to show");

        builder.setMultiChoiceItems(
                appNames,
                checked,
                (dialog, which, isChecked) ->
                        checked[which] = isChecked
        );

        builder.setPositiveButton("SAVE", (dialog, which) -> {

            SharedPreferences.Editor editor = prefs.edit();

            for (int i = 0; i < appNames.length; i++) {
                editor.putBoolean(appNames[i], checked[i]);
            }

            editor.apply();
            buildScreen();
        });

        builder.setNegativeButton("CANCEL", null);

        builder.show();
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
            Intent intent =
                    getPackageManager()
                            .getLaunchIntentForPackage("com.whatsapp");

            if (intent != null) {
                startActivity(intent);
            } else {
                openIntent(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.whatsapp.com")
                ));
            }

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Unable to open WhatsApp",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void openFacebook() {

        try {
            Intent intent =
                    getPackageManager()
                            .getLaunchIntentForPackage("com.facebook.katana");

            if (intent != null) {
                startActivity(intent);
            } else {
                openIntent(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com")
                ));
            }

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Unable to open Facebook",
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
