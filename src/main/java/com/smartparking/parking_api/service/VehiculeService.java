package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.Vehicule;
import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.repository.VehiculeRepository;
import com.smartparking.parking_api.repository.utilisateurRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class VehiculeService {

    private final VehiculeRepository repository;
    private final utilisateurRepository utilisateurRepository;

    public VehiculeService(VehiculeRepository repository,
                           utilisateurRepository utilisateurRepository) {
        this.repository = repository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // ✅ GET ALL
    public List<Vehicule> getAll() {
        return repository.findAll();
    }

    // ✅ GET BY ID
    public Vehicule getById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicule not found"));
    }

    // ✅ CREATE (fix relation 🔥)
    public Vehicule save(Vehicule vehicule) {

        if (vehicule.getUtilisateur() == null) {
            throw new RuntimeException("Utilisateur obligatoire");
        }

        Long userId = vehicule.getUtilisateur().getId();

        utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found"));

        vehicule.setUtilisateur(user);

        return repository.save(vehicule);
    }

    //  DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Vehicule> getUserVehicles(String email){

        utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return repository.findByUtilisateur(user);
    }

    public Vehicule addVehicle(String email, Vehicule vehicule){

        utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String plaque = vehicule.getNumeroPlaque();

        if(plaque == null || plaque.isBlank()){
            throw new RuntimeException("Plaque obligatoire");
        }

        // validation simple plaque
        boolean valid = Pattern.matches(
                "^[A-Za-z0-9\\- ]+$",
                plaque
        );

        if(!valid){
            throw new RuntimeException("Plaque invalide");
        }

        vehicule.setUtilisateur(user);

        return repository.save(vehicule);
    }

    public void deleteVehicle(String email, Long id){

        utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Vehicule vehicule = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vehicule not found"));

        if(!vehicule.getUtilisateur().getId().equals(user.getId())){
            throw new RuntimeException("Accès refusé");
        }

        repository.delete(vehicule);
    }
}