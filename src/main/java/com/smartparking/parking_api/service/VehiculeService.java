package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.Vehicule;
import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.repository.VehiculeRepository;
import com.smartparking.parking_api.repository.utilisateurRepository;

import org.springframework.stereotype.Service;

import java.util.List;

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
}