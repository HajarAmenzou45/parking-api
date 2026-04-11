package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParkingRepository extends JpaRepository<Parking, Integer> {

    @Query("SELECT DISTINCT p FROM Parking p JOIN p.types t WHERE t.type = :type")
    List<Parking> findByType(String type);
}