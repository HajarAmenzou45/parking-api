package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Integer> {
}