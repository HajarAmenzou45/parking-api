package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface utilisateurRepository extends JpaRepository<utilisateur, Long> {
}