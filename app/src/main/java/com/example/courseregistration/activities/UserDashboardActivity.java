package com.example.courseregistration.activities;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.courseregistration.R;

public class UserDashboardActivity extends AppCompatActivity {

    private Button btnViewCourses, btnMyNotifications, btnMyProfile, btnLogoutUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        btnViewCourses = findViewById(R.id.btnViewCourses);
        btnMyNotifications = findViewById(R.id.btnMyNotifications);
        btnMyProfile = findViewById(R.id.btnMyProfile);
        btnLogoutUser = findViewById(R.id.btnUserLogout);

        btnViewCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, CourseListActivity.class));
            }
        });

        btnMyNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboardActivity.this, NotificationActivity.class));
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(UserDashboardActivity.this, ProfileActivity.class));
            }
        });

        btnLogoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to Welcome or Login
            }
        });
    }
}

