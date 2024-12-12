package com.baymotors;

public class Vehicle {
    private int vehicleId;
    private String registrationNumber;
    private String make;
    private String model;
    private Customer owner;

    public Vehicle(int vehicleId, String registrationNumber, String make, String model, Customer owner) {
        this.vehicleId = vehicleId;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.owner = owner;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Customer getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleId + ", Reg: " + registrationNumber +
               ", Make: " + make + ", Model: " + model + ", Owner: " + owner.getName();
    }
}
