package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.place.parking.id = :parkingId AND t.heureSortie IS NULL")
    long countCurrentVehicules(Long parkingId);
}