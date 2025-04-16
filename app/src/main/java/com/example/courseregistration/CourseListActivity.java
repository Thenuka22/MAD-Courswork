package com.example.courseregistration;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseregistration.models.Course;
import com.example.courseregistration.models.CourseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    private RecyclerView rvCourseList;
    private CourseAdapter courseAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        initViews();
        setupRecyclerView();
        loadCourses();
    }

    private void initViews() {
        rvCourseList = findViewById(R.id.rvCourseList);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupRecyclerView() {
        courseAdapter = new CourseAdapter(this);
        rvCourseList.setLayoutManager(new LinearLayoutManager(this));
        rvCourseList.setAdapter(courseAdapter);
    }

    private void loadCourses() {
        // Show loading indicator
        progressBar.setVisibility(View.VISIBLE);

        // In a real app, you would fetch data from API or database
        // Simulating network delay
        rvCourseList.postDelayed(() -> {
            // Hide loading indicator
            progressBar.setVisibility(View.GONE);

            // Load sample courses
            courseAdapter.setCourses(getSampleCourses());
        }, 1000);
    }

    private List<Course> getSampleCourses() {
        List<Course> courses = new ArrayList<>();

        // Sample course 1
        Course course1 = new Course();
        course1.setName("Android App Development");
        course1.setFee("$599");
        course1.setDuration("10 weeks");
        course1.setStartDate("May 5, 2025");
        course1.setClosingDate("April 25, 2025");
        course1.setBranches(Arrays.asList("Downtown", "West Campus"));
        courses.add(course1);

        // Sample course 2
        Course course2 = new Course();
        course2.setName("iOS Development with Swift");
        course2.setFee("$649");
        course2.setDuration("12 weeks");
        course2.setStartDate("June 10, 2025");
        course2.setClosingDate("May 30, 2025");
        course2.setBranches(Arrays.asList("Downtown", "South Side", "East Campus"));
        courses.add(course2);

        // Sample course 3
        Course course3 = new Course();
        course3.setName("Web Development Bootcamp");
        course3.setFee("$499");
        course3.setDuration("8 weeks");
        course3.setStartDate("May 15, 2025");
        course3.setClosingDate("May 5, 2025");
        course3.setBranches(Arrays.asList("All Campuses"));
        courses.add(course3);

        // Sample course 4
        Course course4 = new Course();
        course4.setName("Machine Learning Fundamentals");
        course4.setFee("$799");
        course4.setDuration("14 weeks");
        course4.setStartDate("July 1, 2025");
        course4.setClosingDate("June 15, 2025");
        course4.setBranches(Arrays.asList("Downtown", "North Campus"));
        courses.add(course4);

        return courses;
    }
}