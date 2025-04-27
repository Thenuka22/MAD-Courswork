package com.example.courseregistration.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseregistration.R;
import com.example.courseregistration.database.DatabaseHelper.CourseOffering;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private List<CourseOffering> courseList;
    private OnCourseClickListener listener;
    private boolean isGuest;

    public interface OnCourseClickListener {
        void onCourseClick(CourseOffering course);
    }

    public CourseListAdapter(List<CourseOffering> courseList, OnCourseClickListener listener, boolean isGuest) {
        this.courseList = courseList;
        this.listener = listener;
        this.isGuest = isGuest;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseOffering course = courseList.get(position);
        holder.bind(course);
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvCourseName, tvBranchName, tvStartDate, tvFee;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvBranchName = itemView.findViewById(R.id.tvBranchName);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvFee = itemView.findViewById(R.id.tvFee);
            itemView.setOnClickListener(this);
        }

        void bind(CourseOffering course) {
            tvCourseName.setText(course.getCourseName());
            tvBranchName.setText("Branch: " + course.getBranchName());
            tvStartDate.setText("Start Date: " + course.getStartDate());
            tvFee.setText("Fee: LKR " + course.getCourseFee());
        }

        @Override
        public void onClick(View view) {
            listener.onCourseClick(courseList.get(getAdapterPosition()));
        }
    }
}
