package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaiementRepository extends JpaRepository<Paiement, Integer> {
    @Query("SELECT SUM(p.montant) FROM Paiement p WHERE p.ticket.place.parking.id = :parkingId AND DATE(p.ticket.heureSortie) = CURRENT_DATE")
    Double getTodayRevenueByParking(Long parkingId);
}