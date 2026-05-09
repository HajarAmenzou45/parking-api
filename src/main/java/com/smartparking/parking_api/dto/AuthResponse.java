package com.smartparking.parking_api.dto;

public class AuthResponse {
    public String token;

    public AuthResponse(String token){
        this.token = token;
    }
}