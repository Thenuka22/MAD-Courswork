package com.example.courseregistration.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.courseregistration.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnAddCourse, btnViewUsers, btnManageBranches, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnAddCourse = findViewById(R.id.btnAddCourse);
        btnViewUsers = findViewById(R.id.btnViewUsers);
        btnManageBranches = findViewById(R.id.btnManageBranches);
        btnLogout = findViewById(R.id.btnAdminLogout);

        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AdminDashboardActivity.this, AddCourseActivity.class));
            }
        });

        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AdminDashboardActivity.this, UserListActivity.class));
            }
        });

        btnManageBranches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(AdminDashboardActivity.this, ManageBranchActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to Welcome or Login
            }
        });
    }
}
