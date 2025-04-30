package com.example.courseregistration.activities;



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.adapters.NotificationAdapter;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.Notification;
import com.example.courseregistration.utils.LoggedInUser;

import java.util.List;

public class NotificationActivity extends AppCompatActivity implements NotificationAdapter.OnNotificationClickListener {

    private RecyclerView recyclerViewNotifications;
    private DatabaseHelper dbHelper;
    private List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerViewNotifications = findViewById(R.id.recyclerViewNotifications);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);

        loadNotifications();
    }

    private void loadNotifications() {
        long userId = LoggedInUser.getUserId();
        notificationList = dbHelper.getUserNotifications(userId);

        if (notificationList.isEmpty()) {
            Toast.makeText(this, "No notifications available.", Toast.LENGTH_SHORT).show();
        } else {
            NotificationAdapter adapter = new NotificationAdapter(notificationList, this);
            recyclerViewNotifications.setAdapter(adapter);
        }
    }

    @Override
    public void onNotificationClick(Notification notification) {
        if (!notification.isRead()) {
            dbHelper.markNotificationAsRead(notification.getNotificationId());
            loadNotifications(); // Refresh the list
        }
    }
}
