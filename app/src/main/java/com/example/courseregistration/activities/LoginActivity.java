package com.example.courseregistration.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.User;
import com.example.courseregistration.utils.LoggedInUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etNIC;
    private Button btnLogin;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);

        etEmail = findViewById(R.id.etEmailLogin);
        etNIC = findViewById(R.id.etNICLogin);
        btnLogin = findViewById(R.id.btnLoginNow);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString();
        String nic = etNIC.getText().toString();

        if (email.isEmpty() || nic.isEmpty()) {
            Toast.makeText(this, "Please enter Email and NIC", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = dbHelper.getUserByEmail(email);

        if (user != null && user.getNic().equals(nic)) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();

            if (user.getUserType().equals("ADMIN")) {
                startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
            } else {
                startActivity(new Intent(LoginActivity.this, UserDashboardActivity.class));
            }
            LoggedInUser.setUserId(user.getUserId());
            finish(); // Close login screen
        } else {
            Toast.makeText(this, "Invalid Email or NIC", Toast.LENGTH_SHORT).show();
        }
    }
}
