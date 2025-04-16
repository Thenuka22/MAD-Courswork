package com.example.courseregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnSignIn;
    private Button btnGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        // Initialize UI elements
        btnSignIn = findViewById(R.id.btnSignIn);
        btnGuest = findViewById(R.id.btnGuest);

        // Set up click listeners
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to sign in screen
                navigateToSignIn();
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to main app as guest
                navigateAsGuest();
            }
        });
    }

    private void navigateToSignIn() {
        // Launch sign in activity
        Intent intent = new Intent(WelcomeActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void navigateAsGuest() {
        // For now, let's assume we have a MainActivity for guest users
        // You can replace MainActivity.class with your actual main screen
        Intent intent = new Intent(WelcomeActivity.this, CourseListActivity.class);
        // Pass flag that user is a guest
        intent.putExtra("IS_GUEST", true);
        startActivity(intent);

        // If this is the end of the onboarding flow, you might want to finish this activity
        // finish();
    }
}