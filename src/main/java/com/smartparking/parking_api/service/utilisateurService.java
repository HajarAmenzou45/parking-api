package com.smartparking.parking_api.service;

import com.smartparking.parking_api.dto.LoginRequest;
import com.smartparking.parking_api.dto.RegisterRequest;
import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.enums.RoleUtilisateur;
import com.smartparking.parking_api.repository.utilisateurRepository;
import com.smartparking.parking_api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class utilisateurService {

    @Autowired
    private utilisateurRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public utilisateur register(RegisterRequest request){

        if(repository.findByEmail(request.email).isPresent()){
            throw new RuntimeException("Email déjà utilisé");
        }

        utilisateur u = new utilisateur();
        u.setEmail(request.email);
        u.setNomComplet(request.nomComplet);
        u.setMotDePasse(passwordEncoder.encode(request.motDePasse));
        u.setRole(RoleUtilisateur.CLIENT);

        return repository.save(u);
    }

    public String login(LoginRequest request){

        utilisateur user = repository.findByEmail(request.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.motDePasse, user.getMotDePasse())){
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
}