package com.example.courseregistration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY = 100;
    private static final int REQUEST_IMAGE_CAMERA = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        // Setup all components
        setupDatePicker();
        setupCityDropdown();
        setupImagePickers();

        // Setup submit button
        Button submitButton = findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle form submission
                validateAndSubmit();
            }
        });
    }

    private void validateAndSubmit() {
        // Add your validation logic here
        Toast.makeText(this, "Form submitted successfully!", Toast.LENGTH_SHORT).show();
        // Navigate to next screen or finish activity
    }

    private void setupDatePicker() {
        EditText dateOfBirthField = findViewById(R.id.etDateOfBirth);
        dateOfBirthField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegistrationActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(android.widget.DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                                String formattedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                dateOfBirthField.setText(formattedDate);
                            }
                        },
                        year, month, day);

                // Set max date to today (no future dates)
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    private void setupCityDropdown() {
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia",
                "San Antonio", "San Diego", "Dallas", "San Jose"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, cities);
        AutoCompleteTextView cityDropdown = findViewById(R.id.spinnerCity);
        cityDropdown.setAdapter(adapter);
    }

    private void setupImagePickers() {
        Button galleryButton = findViewById(R.id.btnGallery);
        Button cameraButton = findViewById(R.id.btnCamera);

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAMERA);
                } else {
                    Toast.makeText(RegistrationActivity.this, "No camera app available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImageView = findViewById(R.id.ivProfilePicture);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY && data != null) {
                Uri selectedImageUri = data.getData();
                profileImageView.setImageURI(selectedImageUri);
            } else if (requestCode == REQUEST_IMAGE_CAMERA && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                profileImageView.setImageBitmap(imageBitmap);
            }
        }
    }
}