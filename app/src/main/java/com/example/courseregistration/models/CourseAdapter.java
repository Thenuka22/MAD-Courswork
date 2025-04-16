package com.example.courseregistration.models;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseregistration.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private Context context;
    private List<Course> courses;

    public CourseAdapter(Context context) {
        this.context = context;
        this.courses = new ArrayList<>();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCourseName;
        private TextView tvFee;
        private TextView tvDuration;
        private TextView tvStartDate;
        private TextView tvClosingDate;
        private TextView tvBranches;
        private MaterialButton btnRegister;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvFee = itemView.findViewById(R.id.tvFee);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvClosingDate = itemView.findViewById(R.id.tvClosingDate);
            tvBranches = itemView.findViewById(R.id.tvBranches);
            btnRegister = itemView.findViewById(R.id.btnRegister);
        }

        public void bind(Course course) {
            tvCourseName.setText(course.getName());
            tvFee.setText(course.getFee());
            tvDuration.setText(course.getDuration());
            tvStartDate.setText(course.getStartDate());
            tvClosingDate.setText(course.getClosingDate());

            // Join branch names with commas
            tvBranches.setText(TextUtils.join(", ", course.getBranches()));

            btnRegister.setOnClickListener(v -> {
                // In a real app, you would navigate to registration screen
                Toast.makeText(context, "Registering for " + course.getName(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
