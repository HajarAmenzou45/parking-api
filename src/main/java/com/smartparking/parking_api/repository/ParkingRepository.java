package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Parking;
import com.smartparking.parking_api.enums.VehiculeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {

    List<Parking> findByTypeVehicule(VehiculeType type);
}