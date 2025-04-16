package com.example.courseregistration;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EmailVerificationActivity extends AppCompatActivity {

    private TextInputLayout tilVerificationCode;
    private TextInputEditText etVerificationCode;
    private MaterialButton btnVerify;
    private View tvResendCode;
    private CountDownTimer resendTimer;
    private static final int RESEND_COOLDOWN_SECONDS = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emailverification);

        initViews();
        setupListeners();

        // Optional: Get email from intent
        String email = getIntent().getStringExtra("email");
        // You could customize the message with the user's email if needed
    }

    private void initViews() {
        tilVerificationCode = findViewById(R.id.tilVerificationCode);
        etVerificationCode = findViewById(R.id.etVerificationCode);
        btnVerify = findViewById(R.id.btnVerify);
        tvResendCode = findViewById(R.id.tvResendCode);
    }

    private void setupListeners() {
        btnVerify.setOnClickListener(v -> verifyCode());
        tvResendCode.setOnClickListener(v -> resendVerificationCode());
    }

    private void verifyCode() {
        String code = etVerificationCode.getText().toString().trim();

        if (code.isEmpty()) {
            tilVerificationCode.setError("Please enter verification code");
            return;
        }

        // Clear any previous errors
        tilVerificationCode.setError(null);

        // Here you would implement actual verification logic
        // For example, calling your authentication API

        // Mock success for demonstration
        Toast.makeText(this, "Verification successful!", Toast.LENGTH_SHORT).show();

        // Navigate to next screen on success
        // Intent intent = new Intent(this, NextActivity.class);
        // startActivity(intent);
        // finish();
    }

    private void resendVerificationCode() {
        // Disable resend button and start cooldown timer
        tvResendCode.setEnabled(false);

        // Here you would implement the actual code resend logic
        // For example, calling your authentication API again

        Toast.makeText(this, "Verification code sent again", Toast.LENGTH_SHORT).show();

        // Start cooldown timer
        startResendCooldown();
    }

    private void startResendCooldown() {
        if (resendTimer != null) {
            resendTimer.cancel();
        }

        resendTimer = new CountDownTimer(RESEND_COOLDOWN_SECONDS * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                ((TextView) tvResendCode).setText("Resend code in " + secondsRemaining + "s");
            }

            @Override
            public void onFinish() {
                ((TextView) tvResendCode).setText("Didn't receive the code? Resend");
                tvResendCode.setEnabled(true);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resendTimer != null) {
            resendTimer.cancel();
        }
    }
}