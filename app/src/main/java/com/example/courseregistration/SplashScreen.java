package com.example.courseregistration;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screeen);

        // Get logo and text views
        ImageView logoView = findViewById(R.id.ivLogo);
        TextView textView = findViewById(R.id.tvAppName);

        // Create fade-in animations
        ObjectAnimator logoAnim = ObjectAnimator.ofFloat(logoView, "alpha", 0f, 1f);
        logoAnim.setDuration(1000);

        ObjectAnimator textAnim = ObjectAnimator.ofFloat(textView, "alpha", 0f, 1f);
        textAnim.setDuration(1000);
        textAnim.setStartDelay(500);

        // Start animations
        logoAnim.start();
        textAnim.start();

        // Navigate to welcome screen after a delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, WelcomeActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        }, SPLASH_DURATION);
    }
}