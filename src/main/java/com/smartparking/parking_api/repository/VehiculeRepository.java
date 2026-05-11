package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Vehicule;
import com.smartparking.parking_api.entity.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    List<Vehicule> findByUtilisateur(utilisateur utilisateur);
}