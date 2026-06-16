package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.dto.UpdateProfileRequest;
import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.service.utilisateurService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.smartparking.parking_api.dto.HistoryDTO;
import com.smartparking.parking_api.dto.UserStatsDTO;
import com.smartparking.parking_api.dto.ChangePasswordRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @GetMapping("/history")
    public List<HistoryDTO> history(Authentication auth){

        return service.getHistory(auth.getName());
    }
    @GetMapping("/stats")
    public UserStatsDTO stats(Authentication auth){

        return service.getStats(auth.getName());
    }
    @PutMapping("/change-password")
    public String changePassword(
            Authentication auth,
            @RequestBody ChangePasswordRequest request
    ){

        service.changePassword(
                auth.getName(),
                request
        );

        return "Mot de passe modifié";
    }
    @PostMapping("/profile/photo")
    public utilisateur uploadPhoto(
            Authentication auth,
            @RequestParam("file") MultipartFile file
    ){

        return service.uploadPhoto(
                auth.getName(),
                file
        );
    }

    @GetMapping("/profile/photo/{filename}")
    public ResponseEntity<Resource> getPhoto(
            @PathVariable String filename
    ) throws IOException {

        Path path = Paths.get("uploads").resolve(filename);

        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("Photo introuvable");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @DeleteMapping("/profile/photo")
    public utilisateur deletePhoto(
            Authentication auth
    ){

        return service.deletePhoto(
                auth.getName()
        );
    }
}