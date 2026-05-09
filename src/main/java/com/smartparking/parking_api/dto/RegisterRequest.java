package com.smartparking.parking_api.dto;

public class RegisterRequest {

    private String email;
    private String motDePasse;
    private String nomComplet;

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getNomComplet() {
        return nomComplet;
    }
}