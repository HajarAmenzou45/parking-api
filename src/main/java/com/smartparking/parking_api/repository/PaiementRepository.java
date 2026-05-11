package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Paiement;
import com.smartparking.parking_api.entity.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Integer> {

    @Query("""
        SELECT SUM(p.montant)
        FROM Paiement p
        WHERE p.ticket.place.parking.id = :parkingId
    """)
    Double getTodayRevenueByParking(Long parkingId);

    @Query("""
        SELECT p
        FROM Paiement p
        WHERE p.ticket.utilisateur = :utilisateur
    """)
    List<Paiement> findUserHistory(utilisateur utilisateur);
}