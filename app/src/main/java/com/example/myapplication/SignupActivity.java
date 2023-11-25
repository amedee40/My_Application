package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(view -> {

            // Get the username from the EditText
            String username = signupUsername.getText().toString();

            // Check if the username already exists
            checkUsernameExists(username);
        });

        loginRedirectText.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, Passenger_login.class);
            startActivity(intent);
        });
    }

    private void checkUsernameExists(String username) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        reference.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // The username already exists in the database
                    Toast.makeText(SignupActivity.this, "Username already exists. Please choose a different one.", Toast.LENGTH_SHORT).show();
                } else {
                    // The username is not in the database, proceed with signup
                    signup();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(SignupActivity.this, "Database error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signup() {
        // Continue with the signup process

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        String name = signupName.getText().toString();
        String email = signupEmail.getText().toString();
        String username = signupUsername.getText().toString();
        String password = signupPassword.getText().toString();

        ClassUser helperClass = new ClassUser(name, email, username, password);
        reference.child(username).setValue(helperClass);

        Toast.makeText(SignupActivity.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignupActivity.this, Passenger_login.class);
        startActivity(intent);
    }
}
