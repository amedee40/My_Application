package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.Driver;

public class Welcome_page extends AppCompatActivity {
    Button btnuser;
    Button btndriver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        btnuser = (Button) findViewById(R.id.user);
        btndriver = (Button) findViewById(R.id.driver);

        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // // Handle user button click, navigate to UserLoginActivity
                Intent intent = new Intent(Welcome_page.this, Passenger_login.class);
                startActivity(intent);
                finish();


            }
        });
        btndriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome_page.this, Driver_Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}