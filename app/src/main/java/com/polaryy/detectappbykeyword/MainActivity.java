package com.polaryy.detectappbykeyword;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static Activity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentActivity = this;
        DetectApp.instance.GetDexForKeywordsOfApps();
    }

    public static void ShowMessageBox(String message) {
        new AlertDialog.Builder(currentActivity)
                .setCancelable(false)
                .setTitle("Error")
                .setMessage(message)
                .setNegativeButton("ok", (dialog, i) -> System.exit(0))
                .show();
    }
}