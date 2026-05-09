package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface utilisateurRepository extends JpaRepository<utilisateur, Long> {
    Optional<utilisateur> findByEmail(String email);

}