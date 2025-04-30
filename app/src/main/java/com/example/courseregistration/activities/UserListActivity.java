package com.example.courseregistration.activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.adapters.UserListAdapter;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.User;

import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private DatabaseHelper dbHelper;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        loadUsers();
    }

    private void loadUsers() {
        userList = dbHelper.getAllUsers();

        if (userList.isEmpty()) {
            Toast.makeText(this, "No users registered yet.", Toast.LENGTH_SHORT).show();
        } else {
            UserListAdapter adapter = new UserListAdapter(userList);
            recyclerViewUsers.setAdapter(adapter);
        }
    }
}
