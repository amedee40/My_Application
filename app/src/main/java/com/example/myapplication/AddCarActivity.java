package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddCarActivity extends AppCompatActivity {

    private EditText carNameEditText;
    private EditText carModelEditText;
    private EditText carNumberEditText;
    private Switch locationSwitch;
    private Button addButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_information);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        carNameEditText = findViewById(R.id.car_name);
        carModelEditText = findViewById(R.id.car_model);
        carNumberEditText = findViewById(R.id.car_number);
        locationSwitch = findViewById(R.id.switch1);
        addButton = findViewById(R.id.button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve data from EditText fields and Switch
                String carName = carNameEditText.getText().toString();
                String carModel = carModelEditText.getText().toString();
                String carNumber = carNumberEditText.getText().toString();
                boolean locationEnabled = locationSwitch.isChecked();

                // Set default location
                LatLng defaultLocation = new LatLng(-29.846465878373067, 31.000507204169015);

                // Create a new Car object with the entered data
                Bus newCar = new Bus(carName, carModel, carNumber, locationEnabled);
                String carId = databaseReference.child("cars").push().getKey();

                try {
                    // Insert data into Firebase
                    databaseReference.child("cars").child(carId).setValue(newCar);

                    // Pass carId and default location to MapsFragment
                    Intent intent = new Intent(AddCarActivity.this, ActivityDriver.class);
                    intent.putExtra("carId", carId); // Pass the carId to identify the car
                    intent.putExtra("defaultLocation", defaultLocation); // Pass the default location
                    startActivity(intent);

                    // Optionally, you can add success handling logic here
                    // For example, display a success message or handle other actions.
                } catch (Exception e) {
                    // Handle the exception (e.g., log the error or display an error message)
                    Log.e("MyApp", "Error: " + e.getMessage());

                    // Optionally, you can add failure handling logic here
                    // For example, display an error message to the user.
                }
            }
        });



    }
}