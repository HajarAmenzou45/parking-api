package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Reservation;
import com.smartparking.parking_api.entity.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository
        extends JpaRepository<Reservation, Integer> {

    long countByUtilisateur(utilisateur utilisateur);
}