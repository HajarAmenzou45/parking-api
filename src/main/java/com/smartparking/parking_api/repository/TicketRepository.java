package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}