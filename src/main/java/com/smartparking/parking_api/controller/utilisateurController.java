package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.dto.UpdateProfileRequest;
import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.service.utilisateurService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class utilisateurController {

    @Autowired
    private utilisateurService service;

    @GetMapping
    public List<utilisateur> getAll(){
        return service.getAll();
    }

    @GetMapping("/me")
    public utilisateur getCurrentUser(HttpServletRequest request){
        String email = (String) request.getAttribute("email");
        return service.getByEmail(email);
    }

    @GetMapping("/profile")
    public utilisateur profile(Authentication auth){

        return service.getCurrentUser(auth.getName());
    }

    @PutMapping("/profile")
    public utilisateur updateProfile(
            Authentication auth,
            @RequestBody UpdateProfileRequest request
    ){

        return service.updateProfile(
                auth.getName(),
                request
        );
    }
}