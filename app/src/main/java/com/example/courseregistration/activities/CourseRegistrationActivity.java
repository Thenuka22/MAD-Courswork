package com.example.courseregistration.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.CourseOffering;
import com.example.courseregistration.database.DatabaseHelper.PromotionCode;
import com.example.courseregistration.database.DatabaseHelper.Registration;
import com.example.courseregistration.utils.LoggedInUser;

public class CourseRegistrationActivity extends AppCompatActivity {

    private TextView tvCourseName, tvBranchName, tvFee, tvStartDate;
    private EditText etPromoCode;
    private Button btnRegisterCourse;
    private DatabaseHelper dbHelper;
    private long offeringId;
    private double courseFee;
    private Long promoId = null;
    private double discountAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_registration);

        dbHelper = new DatabaseHelper(this);

        tvCourseName = findViewById(R.id.tvCourseNameReg);
        tvBranchName = findViewById(R.id.tvBranchNameReg);
        tvFee = findViewById(R.id.tvCourseFeeReg);
        tvStartDate = findViewById(R.id.tvStartDateReg);
        etPromoCode = findViewById(R.id.etPromoCode);
        btnRegisterCourse = findViewById(R.id.btnRegisterCourse);

        offeringId = getIntent().getLongExtra("offeringId", -1);

        if (offeringId == -1) {
            Toast.makeText(this, "Invalid course selected!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadCourseDetails();

        btnRegisterCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForCourse();
            }
        });
    }

    private void loadCourseDetails() {
        CourseOffering course = dbHelper.getCourseOffering(offeringId);

        if (course != null) {
            tvCourseName.setText(course.getCourseName());
            tvBranchName.setText("Branch: " + course.getBranchName());
            tvFee.setText("Course Fee: LKR " + course.getCourseFee());
            tvStartDate.setText("Starting Date: " + course.getStartDate());
            courseFee = course.getCourseFee();
        }
    }

    private void registerForCourse() {
        String promoCodeInput = etPromoCode.getText().toString().trim();

        if (!promoCodeInput.isEmpty()) {
            PromotionCode promo = dbHelper.getPromotionByCode(promoCodeInput);
            if (promo != null && promo.isActive()) {
                promoId = promo.getPromoId();
                discountAmount = (courseFee * promo.getDiscountPercentage()) / 100.0;
            } else {
                Toast.makeText(this, "Invalid or expired Promo Code!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        long loggedInUserId = LoggedInUser.getUserId(); // You must manage current logged-in user globally!

        Registration registration = new Registration();
        registration.setUserId(loggedInUserId);
        registration.setOfferingId(offeringId);
        registration.setPromoId(promoId);
        registration.setAmountPaid(courseFee - discountAmount);
        registration.setDiscountAmount(discountAmount);
        registration.setConfirmed(true); // Auto confirm
        registration.setConfirmationEmailSent(false); // Optional future feature

        long registrationId = dbHelper.createRegistration(registration);

        if (registrationId != -1) {
            Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
            finish(); // Go back to dashboard
        } else {
            Toast.makeText(this, "Registration Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
