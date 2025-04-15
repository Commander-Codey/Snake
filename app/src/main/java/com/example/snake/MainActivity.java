package com.example.snake;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the accessibility service is enabled
        if (!isAccessibilityServiceEnabled(MainActivity.this, AppDetectionService.class)) {
            // Prompt the user to enable the Accessibility service
            Toast.makeText(this, "Please enable the Snake Accessibility Service for this app to work", Toast.LENGTH_LONG).show();
            openAccessibilitySettings();
        } else {
            Toast.makeText(this, "Snake service is active!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAccessibilityServiceEnabled(Context context, Class<?> service) {
        int colonIndex = service.getName().lastIndexOf(':');
        String colonStrippedService = service.getName().substring(colonIndex + 1);

        String enabledServices = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        if (TextUtils.isEmpty(enabledServices)) {
            return false;
        }
        String colonStrippedServiceName = colonStrippedService.toLowerCase();
        String[] colonStrippedServices = enabledServices.split(":");
        for (String enabledService : colonStrippedServices) {
            if (enabledService.equalsIgnoreCase(colonStrippedServiceName)) {
                return true;
            }
        }
        return false;
    }

    private void openAccessibilitySettings() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }
}
