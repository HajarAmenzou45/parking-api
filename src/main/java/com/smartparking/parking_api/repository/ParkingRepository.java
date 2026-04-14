package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Parking;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Integer> {

    // 🔥 FILTER BY TYPE (IMPORTANT)
    @Query("SELECT DISTINCT p FROM Parking p JOIN p.types t WHERE t.type = :type")
    List<Parking> findByType(@Param("type") String type);

}