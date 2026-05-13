package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.dto.*;
import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.service.utilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class authController {

    @Autowired
    private utilisateurService service;

    @PostMapping("/register")
    public utilisateur register(@RequestBody RegisterRequest request){
        return service.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        String token = service.login(request);
        return new AuthResponse(token);
    }
    @PostMapping("/logout")
    public String logout() {

        return "Déconnexion réussie";
    }
}