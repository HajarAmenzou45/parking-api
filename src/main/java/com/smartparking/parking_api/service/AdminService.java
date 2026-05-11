package com.smartparking.parking_api.service;

import com.smartparking.parking_api.dto.AdminStatsDTO;

import com.smartparking.parking_api.repository.PaiementRepository;
import com.smartparking.parking_api.repository.ReservationRepository;
import com.smartparking.parking_api.repository.TicketRepository;
import com.smartparking.parking_api.repository.utilisateurRepository;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final utilisateurRepository utilisateurRepository;
    private final ReservationRepository reservationRepository;
    private final TicketRepository ticketRepository;
    private final PaiementRepository paiementRepository;

    public AdminService(
            utilisateurRepository utilisateurRepository,
            ReservationRepository reservationRepository,
            TicketRepository ticketRepository,
            PaiementRepository paiementRepository
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
        this.paiementRepository = paiementRepository;
    }

    public AdminStatsDTO getStats(){

        AdminStatsDTO dto =
                new AdminStatsDTO();

        dto.users =
                utilisateurRepository.count();

        dto.reservations =
                reservationRepository.count();

        dto.tickets =
                ticketRepository.count();

        dto.paiements =
                paiementRepository.count();

        dto.revenue =
                paiementRepository.getTotalRevenue();

        return dto;
    }
}