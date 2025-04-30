package com.example.courseregistration.activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.adapters.BranchListAdapterAdmin;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.Branch;

import java.util.List;

public class ManageBranchActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBranchesAdmin;
    private EditText etBranchCode, etBranchName, etBranchLocation, etLatitude, etLongitude;
    private Button btnAddBranch;
    private DatabaseHelper dbHelper;
    private List<Branch> branchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_branch);

        recyclerViewBranchesAdmin = findViewById(R.id.recyclerViewBranchesAdmin);
        recyclerViewBranchesAdmin.setLayoutManager(new LinearLayoutManager(this));

        etBranchCode = findViewById(R.id.etBranchCode);
        etBranchName = findViewById(R.id.etBranchName);
        etBranchLocation = findViewById(R.id.etBranchLocation);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        btnAddBranch = findViewById(R.id.btnAddBranch);

        dbHelper = new DatabaseHelper(this);

        loadBranches();

        btnAddBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBranch();
            }
        });
    }

    private void loadBranches() {
        branchList = dbHelper.getAllBranches();

        BranchListAdapterAdmin adapter = new BranchListAdapterAdmin(branchList);
        recyclerViewBranchesAdmin.setAdapter(adapter);
    }

    private void addBranch() {
        String branchCode = etBranchCode.getText().toString().trim();
        String branchName = etBranchName.getText().toString().trim();
        String location = etBranchLocation.getText().toString().trim();
        String latStr = etLatitude.getText().toString().trim();
        String lonStr = etLongitude.getText().toString().trim();

        if (branchCode.isEmpty() || branchName.isEmpty() || location.isEmpty() || latStr.isEmpty() || lonStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude = Double.parseDouble(latStr);
        double longitude = Double.parseDouble(lonStr);

        Branch branch = new Branch();
        branch.setBranchCode(branchCode);
        branch.setBranchName(branchName);
        branch.setLocation(location);
        branch.setLatitude(latitude);
        branch.setLongitude(longitude);

        long branchId = dbHelper.createBranch(branch);

        if (branchId != -1) {
            Toast.makeText(this, "Branch added successfully!", Toast.LENGTH_SHORT).show();
            loadBranches(); // Refresh list
            clearFields();
        } else {
            Toast.makeText(this, "Failed to add branch.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etBranchCode.setText("");
        etBranchName.setText("");
        etBranchLocation.setText("");
        etLatitude.setText("");
        etLongitude.setText("");
    }
}
