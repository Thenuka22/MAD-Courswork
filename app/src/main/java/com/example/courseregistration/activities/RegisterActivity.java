package com.example.courseregistration.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.example.courseregistration.database.DatabaseHelper;
import com.example.courseregistration.database.DatabaseHelper.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etName, etAddress, etCity, etDOB, etNIC, etEmail, etPhone;
    private Spinner spinnerGender;
    private ImageView ivProfilePicture;
    private Button btnUploadImage, btnRegister;
    //private Switch switchSetAdmin;
    private byte[] profilePictureBytes = null;
    private DatabaseHelper dbHelper;

    private boolean isAdminMode = false; // Set to true in development mode only

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etDOB = findViewById(R.id.etDOB);
        etNIC = findViewById(R.id.etNIC);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnRegister = findViewById(R.id.btnRegister);
        spinnerGender = findViewById(R.id.spinnerGender);
       // switchSetAdmin = findViewById(R.id.switchSetAdmin);

        // Gender dropdown
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Male", "Female", "Other"});
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        // Show Date Picker on DOB click
        etDOB.setOnClickListener(v -> showDatePicker());

        // Toggle switch visibility for admin mode
        if (isAdminMode) {
            //switchSetAdmin.setVisibility(View.VISIBLE);
        } else {
           // switchSetAdmin.setVisibility(View.GONE);
        }

        btnUploadImage.setOnClickListener(view -> openImageChooser());

        btnRegister.setOnClickListener(view -> registerUser());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String dob = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                    etDOB.setText(dob);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
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
                ivProfilePicture.setImageBitmap(bitmap);
                profilePictureBytes = getBytesFromBitmap(bitmap);
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

    private void registerUser() {
        String name = etName.getText().toString();
        String address = etAddress.getText().toString();
        String city = etCity.getText().toString();
        String dob = etDOB.getText().toString();
        String nic = etNIC.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String gender = spinnerGender.getSelectedItem().toString();
        //boolean isAdmin = switchSetAdmin.isChecked();

        if (name.isEmpty() || address.isEmpty() || city.isEmpty() || dob.isEmpty()
                || nic.isEmpty() || email.isEmpty() || gender.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User();
        user.setName(name);
        user.setAddress(address);
        user.setCity(city);
        user.setDateOfBirth(dob);
        user.setNic(nic);
        user.setEmail(email);
        user.setGender(gender);
        user.setPhone(phone);
        user.setProfilePicture(profilePictureBytes);
        user.setVerified(false);
        //user.setUserType(isAdmin ? "ADMIN" : "USER");

        long userId = dbHelper.createUser(user);

        if (userId != -1) {
            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
