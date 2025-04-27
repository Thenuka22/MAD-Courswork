package com.example.courseregistration.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.Branch;
import com.example.courseregistration.adapters.BranchListAdapter;

import java.util.List;

public class BranchListActivity extends AppCompatActivity implements BranchListAdapter.OnBranchClickListener {

    private RecyclerView recyclerViewBranches;
    private DatabaseHelper dbHelper;
    private List<Branch> branchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_list);

        recyclerViewBranches = findViewById(R.id.recyclerViewBranches);
        recyclerViewBranches.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
        loadBranches();
    }

    private void loadBranches() {
        branchList = dbHelper.getAllBranches();

        if (branchList.isEmpty()) {
            Toast.makeText(this, "No branches available.", Toast.LENGTH_SHORT).show();
        } else {
            BranchListAdapter adapter = new BranchListAdapter(branchList, this);
            recyclerViewBranches.setAdapter(adapter);
        }
    }

    @Override
    public void onBranchClick(Branch branch) {
        // Open location in Google Maps
        Uri gmmIntentUri = Uri.parse("geo:" + branch.getLatitude() + "," + branch.getLongitude() +
                "?q=" + Uri.encode(branch.getBranchName()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps app is not installed", Toast.LENGTH_SHORT).show();
        }
    }
}

