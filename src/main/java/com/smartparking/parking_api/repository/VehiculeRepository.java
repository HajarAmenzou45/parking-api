package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}

