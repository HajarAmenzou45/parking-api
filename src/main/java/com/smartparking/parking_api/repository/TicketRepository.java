package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Ticket;
import com.smartparking.parking_api.enums.StatutTicket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE t.place.parking.id = :parkingId
        AND t.statut = 'OUVERT'
    """)
    long countCurrentVehicules(Long parkingId);

    Optional<Ticket> findByUtilisateurEmailAndStatut(
            String email,
            StatutTicket statut
    );
}