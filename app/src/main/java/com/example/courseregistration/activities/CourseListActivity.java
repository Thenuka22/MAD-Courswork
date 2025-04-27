package com.example.courseregistration.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.CourseOffering;
import com.example.courseregistration.adapters.CourseListAdapter;

import java.util.List;

public class CourseListActivity extends AppCompatActivity implements CourseListAdapter.OnCourseClickListener {

    private RecyclerView recyclerViewCourses;
    private DatabaseHelper dbHelper;
    private List<CourseOffering> courseList;
    private boolean isGuest = false; // Default: user mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        recyclerViewCourses = findViewById(R.id.recyclerViewCourses);
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        if (getIntent() != null && getIntent().getBooleanExtra("isGuest", false)) {
            isGuest = true;
        }

        loadCourses();
    }

    private void loadCourses() {
        courseList = dbHelper.getAllActiveCourseOfferings();

        if (courseList.isEmpty()) {
            Toast.makeText(this, "No active courses available.", Toast.LENGTH_SHORT).show();
        } else {
            CourseListAdapter adapter = new CourseListAdapter(courseList, this, isGuest);
            recyclerViewCourses.setAdapter(adapter);
        }
    }

    @Override
    public void onCourseClick(CourseOffering course) {
        if (isGuest) {
            Toast.makeText(this, "Please register or login to register for courses.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(CourseListActivity.this, CourseRegistrationActivity.class);
            intent.putExtra("offeringId", course.getOfferingId());
            startActivity(intent);
        }
    }
}
