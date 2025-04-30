package com.example.courseregistration.activities;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.User;
import com.example.courseregistration.utils.LoggedInUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView tvName, tvEmail, tvNIC, tvPhone, tvCity, tvDOB, tvGender;
    private ImageView ivProfileImage;
    private Button btnChangePicture;
    private DatabaseHelper dbHelper;
    private User user;
    private byte[] newProfileImageBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvProfileName);
        tvEmail = findViewById(R.id.tvProfileEmail);
        tvNIC = findViewById(R.id.tvProfileNIC);
        tvPhone = findViewById(R.id.tvProfilePhone);
        tvCity = findViewById(R.id.tvProfileCity);
        tvDOB = findViewById(R.id.tvProfileDOB);
        tvGender = findViewById(R.id.tvProfileGender);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        btnChangePicture = findViewById(R.id.btnChangePicture);

        dbHelper = new DatabaseHelper(this);

        loadUserProfile();

        btnChangePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });
    }

    private void loadUserProfile() {
        long userId = LoggedInUser.getUserId();
        user = dbHelper.getUser(userId);

        if (user != null) {
            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());
            tvNIC.setText(user.getNic());
            tvPhone.setText(user.getPhone());
            tvCity.setText(user.getCity());
            tvDOB.setText(user.getDateOfBirth());
            tvGender.setText(user.getGender());

            if (user.getProfilePicture() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getProfilePicture(), 0, user.getProfilePicture().length);
                ivProfileImage.setImageBitmap(bitmap);
            }
        }
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                ivProfileImage.setImageBitmap(bitmap);
                newProfileImageBytes = getBytesFromBitmap(bitmap);

                // Update profile picture in database
                if (newProfileImageBytes != null) {
                    long userId = LoggedInUser.getUserId();
                    int result = dbHelper.updateUserProfilePicture(userId, newProfileImageBytes);

                    if (result > 0) {
                        Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to update picture", Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
