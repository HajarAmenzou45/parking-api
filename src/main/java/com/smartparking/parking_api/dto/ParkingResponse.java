package com.smartparking.parking_api.dto;

import com.smartparking.parking_api.entity.Parking;

public class ParkingResponse {

    private Parking parking;
    private double distance;

    public ParkingResponse(Parking parking, double distance) {
        this.parking = parking;
        this.distance = distance;
    }

    public Parking getParking() {
        return parking;
    }

    public double getDistance() {
        return distance;
    }
}