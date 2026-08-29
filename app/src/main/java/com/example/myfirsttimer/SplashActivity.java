package com.example.myfirsttimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.example.myfirsttimer.ui.auth.LoginActivity;
import com.example.myfirsttimer.util.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 1600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        long exitTime = SystemClock.uptimeMillis() + SPLASH_DURATION;
        splashScreen.setKeepOnScreenCondition(() -> SystemClock.uptimeMillis() < exitTime);

        setContentView(R.layout.activity_splash);

        boolean loggedIn = new SessionManager(this).isLoggedIn();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(this, loggedIn ? MainActivity.class : LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}
