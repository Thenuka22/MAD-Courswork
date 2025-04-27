package com.example.courseregistration.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.courseregistration.R;

public class GuestDashboardActivity extends AppCompatActivity {

    private Button btnViewCoursesGuest, btnViewBranchesGuest, btnGuestExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_dashboard);

        btnViewCoursesGuest = findViewById(R.id.btnViewCoursesGuest);
        btnViewBranchesGuest = findViewById(R.id.btnViewBranchesGuest);
        btnGuestExit = findViewById(R.id.btnGuestExit);

        btnViewCoursesGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestDashboardActivity.this, CourseListActivity.class);
                intent.putExtra("isGuest", true); // indicate guest mode
                startActivity(intent);
            }
        });

        btnViewBranchesGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestDashboardActivity.this, BranchListActivity.class);
                startActivity(intent);
            }
        });

        btnGuestExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Exit guest dashboard
            }
        });
    }
}
