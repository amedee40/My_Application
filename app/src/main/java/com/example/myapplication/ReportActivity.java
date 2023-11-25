package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// Assuming you have Firebase initialized in your app

public class ReportActivity extends AppCompatActivity {

    private DatabaseReference reportsRef;
    private EditText reportTypeEditText, reportDescEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Initialize Firebase references
        reportsRef = FirebaseDatabase.getInstance().getReference().child("Reports");

        reportTypeEditText = findViewById(R.id.report_type_edit_text);
        reportDescEditText = findViewById(R.id.report_desc_edit_text);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReport();
            }
        });
    }

    private void submitReport() {
        String reportType = reportTypeEditText.getText().toString().trim();
        String reportDesc = reportDescEditText.getText().toString().trim();

        if (!TextUtils.isEmpty(reportType) && !TextUtils.isEmpty(reportDesc)) {
            // Generate a unique ID for the report
            String reportId = reportsRef.push().getKey();

            // Create a report object
            Report newReport = new Report(reportId, reportType, reportDesc, System.currentTimeMillis());

            // Push the report to Firebase Database
            reportsRef.child(reportId).setValue(newReport)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {

                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ReportActivity.this, "Report submitted successfully", Toast.LENGTH_SHORT).show();
                            // Optionally, you can perform any UI update or navigation here
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ReportActivity.this, "Failed to submit report", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
