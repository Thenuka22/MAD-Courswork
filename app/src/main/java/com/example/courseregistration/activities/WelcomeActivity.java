package com.example.courseregistration.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.courseregistration.R;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister, btnGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Initialize UI components
        initializeViews();

        // Set click listeners
        setupClickListeners();
    }

    private void initializeViews() {
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnGuest = findViewById(R.id.btnGuest);
    }

    private void setupClickListeners() {
        // Login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to login screen
                Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                 startActivity(loginIntent);
            }
        });

        // Register button click listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to register screen
                Intent registerIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        // Guest button click listener
        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to main app screen as guest
                Intent mainIntent = new Intent(WelcomeActivity.this, UserDashboardActivity.class);
                mainIntent.putExtra("USER_TYPE", "GUEST");
                startActivity(mainIntent);
                finish(); // Close welcome activity so user can't go back
            }
        });
    }
}