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

public class BranchListAdapterAdmin extends RecyclerView.Adapter<BranchListAdapterAdmin.BranchViewHolder> {

    private List<Branch> branchList;

    public BranchListAdapterAdmin(List<Branch> branchList) {
        this.branchList = branchList;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_branch_admin, parent, false);
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

    class BranchViewHolder extends RecyclerView.ViewHolder {
        TextView tvBranchName, tvBranchLocation;

        public BranchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBranchName = itemView.findViewById(R.id.tvBranchNameAdmin);
            tvBranchLocation = itemView.findViewById(R.id.tvBranchLocationAdmin);
        }

        void bind(Branch branch) {
            tvBranchName.setText(branch.getBranchName() + " (" + branch.getBranchCode() + ")");
            tvBranchLocation.setText(branch.getLocation());
        }
    }
}
