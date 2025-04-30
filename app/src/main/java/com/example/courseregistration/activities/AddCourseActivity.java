package com.example.courseregistration.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.Course;

public class AddCourseActivity extends AppCompatActivity {

    private EditText etCourseName, etCourseFee, etDuration, etMaxParticipants, etPublishedDate;
    private Button btnSaveCourse;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        etCourseName = findViewById(R.id.etCourseName);
        etCourseFee = findViewById(R.id.etCourseFee);
        etDuration = findViewById(R.id.etDuration);
        etMaxParticipants = findViewById(R.id.etMaxParticipants);
        etPublishedDate = findViewById(R.id.etPublishedDate);
        btnSaveCourse = findViewById(R.id.btnSaveCourse);

        dbHelper = new DatabaseHelper(this);

        btnSaveCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCourse();
            }
        });
    }

    private void saveCourse() {
        String name = etCourseName.getText().toString().trim();
        String feeStr = etCourseFee.getText().toString().trim();
        String durationStr = etDuration.getText().toString().trim();
        String maxParticipantsStr = etMaxParticipants.getText().toString().trim();
        String publishedDate = etPublishedDate.getText().toString().trim(); // yyyy-mm-dd

        if (name.isEmpty() || feeStr.isEmpty() || durationStr.isEmpty() || maxParticipantsStr.isEmpty() || publishedDate.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double fee = Double.parseDouble(feeStr);
        int duration = Integer.parseInt(durationStr);
        int maxParticipants = Integer.parseInt(maxParticipantsStr);

        Course course = new Course();
        course.setCourseName(name);
        course.setCourseFee(fee);
        course.setDuration(duration);
        course.setMaxParticipants(maxParticipants);
        course.setPublishedDate(publishedDate);

        long courseId = dbHelper.createCourse(course);

        if (courseId != -1) {
            Toast.makeText(this, "Course added successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add course.", Toast.LENGTH_SHORT).show();
        }
    }
}

