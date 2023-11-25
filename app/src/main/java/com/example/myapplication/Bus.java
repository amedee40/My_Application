package com.example.myapplication;

public class Bus {
    private String carName;
    private String carModel;
    private String carNumber;
    private boolean locationEnabled;

    // Required default constructor for Firebase
    public Bus() {
    }

    // Your parameterized constructor
    public Bus(String carName, String carModel, String carNumber, boolean locationEnabled) {
        this.carName = carName;
        this.carModel = carModel;
        this.carNumber = carNumber;
        this.locationEnabled = locationEnabled;
    }

    // Getter for carName
    public String getCarName() {
        return carName;
    }

    // Setter for carName
    public void setCarName(String carName) {
        this.carName = carName;
    }

    // Getter for carModel
    public String getCarModel() {
        return carModel;
    }

    // Setter for carModel
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    // Getter for carNumber
    public String getCarNumber() {
        return carNumber;
    }

    // Setter for carNumber
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    // Getter for locationEnabled
    public boolean isLocationEnabled() {
        return locationEnabled;
    }

    // Setter for locationEnabled
    public void setLocationEnabled(boolean locationEnabled) {
        this.locationEnabled = locationEnabled;
    }
}
