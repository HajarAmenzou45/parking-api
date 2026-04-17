package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Place;
import com.smartparking.parking_api.enums.StatutPlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByParkingId(Long parkingId);
    long countByParkingIdAndStatut(Long parkingId, StatutPlace statut);

    long countByParkingId(Long parkingId);
}

