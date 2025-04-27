package com.example.courseregistration.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseregistration.R;
import com.example.courseregistration.database.DatabaseHelper.Branch;

import java.util.List;

public class BranchListAdapter extends RecyclerView.Adapter<BranchListAdapter.BranchViewHolder> {

    private List<Branch> branchList;
    private OnBranchClickListener listener;

    public interface OnBranchClickListener {
        void onBranchClick(Branch branch);
    }

    public BranchListAdapter(List<Branch> branchList, OnBranchClickListener listener) {
        this.branchList = branchList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_branch, parent, false);
        return new BranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        Branch branch = branchList.get(position);
        holder.bind(branch);
    }

    @Override
    public int getItemCount() {
        return branchList.size();
    }

    class BranchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvBranchName, tvBranchLocation;

        public BranchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBranchName = itemView.findViewById(R.id.tvBranchName);
            tvBranchLocation = itemView.findViewById(R.id.tvBranchLocation);
            itemView.setOnClickListener(this);
        }

        void bind(Branch branch) {
            tvBranchName.setText(branch.getBranchName());
            tvBranchLocation.setText(branch.getLocation());
        }

        @Override
        public void onClick(View view) {
            listener.onBranchClick(branchList.get(getAdapterPosition()));
        }
    }
}
