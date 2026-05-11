package com.smartparking.parking_api.service;

import com.smartparking.parking_api.dto.LoginRequest;
import com.smartparking.parking_api.dto.RegisterRequest;
import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.enums.RoleUtilisateur;
import com.smartparking.parking_api.repository.utilisateurRepository;
import com.smartparking.parking_api.security.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.smartparking.parking_api.dto.UpdateProfileRequest;

import java.util.List;

@Service
public class utilisateurService {

    private final utilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;

    public utilisateurService(utilisateurRepository repository,
                              PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER
    public utilisateur register(RegisterRequest request){

        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException("Email déjà utilisé");
        }

        utilisateur u = new utilisateur();
        u.setEmail(request.getEmail());
        u.setNomComplet(request.getNomComplet());
        u.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        u.setRole(RoleUtilisateur.CLIENT);

        return repository.save(u);
    }

    // LOGIN
    public String login(LoginRequest request){

        utilisateur user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())){
            throw new RuntimeException("Password incorrect");
        }

        return JwtUtil.generateToken(user.getEmail());
    }

    public List<utilisateur> getAll(){
        return repository.findAll();
    }

    public utilisateur getByEmail(String email){
        return repository.findByEmail(email).orElse(null);
    }

    public utilisateur getCurrentUser(String email){
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public utilisateur updateProfile(
            String email,
            UpdateProfileRequest request
    ){

        utilisateur user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setNomComplet(request.nomComplet);
        user.setUsername(request.username);
        user.setTelephone(request.telephone);
        user.setGenre(request.genre);
        user.setCarteBancaire(request.carteBancaire);

        return repository.save(user);
    }
}