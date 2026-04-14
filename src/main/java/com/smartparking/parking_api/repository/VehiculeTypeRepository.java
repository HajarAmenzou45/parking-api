package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.VehiculeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeTypeRepository extends JpaRepository<VehiculeType,Integer> {
}