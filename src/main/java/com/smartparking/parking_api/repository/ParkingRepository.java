package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Parking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Integer> {

    @Query("SELECT DISTINCT p FROM Parking p LEFT JOIN FETCH p.types")
    List<Parking> findAll();

    @Query("SELECT DISTINCT p FROM Parking p JOIN FETCH p.types t WHERE t.type = :type")
    List<Parking> findByType(String type);
}