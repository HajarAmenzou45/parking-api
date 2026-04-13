package com.smartparking.parking_api.dto;

import com.smartparking.parking_api.entity.Parking;
import com.smartparking.parking_api.entity.VehiculeType;

import java.util.List;

public class ParkingResponse {

    private Integer id;
    private String nom;
    private String adresse;
    private Double latitude;
    private Double longitude;
    private Double prixParHeure;
    private List<VehiculeType> types;

    private double distance;

    public ParkingResponse(Parking p, double distance) {
        this.id = p.getId();
        this.nom = p.getNom();
        this.adresse = p.getAdresse();
        this.latitude = p.getLatitude();
        this.longitude = p.getLongitude();
        this.prixParHeure = p.getPrixParHeure();
        this.types = p.getTypes();
        this.distance = distance;
    }

    // getters
    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public String getAdresse() { return adresse; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Double getPrixParHeure() { return prixParHeure; }
    public List<VehiculeType> getTypes() { return types; }
    public double getDistance() { return distance; }
}