package com.smartparking.parking_api.service;

import com.smartparking.parking_api.dto.PaymentSummaryDTO;

import com.smartparking.parking_api.entity.*;

import com.smartparking.parking_api.enums.*;

import com.smartparking.parking_api.repository.*;

import org.springframework.stereotype.Service;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepo;
    private final TicketRepository ticketRepo;
    private final NotificationService notificationService;

    public PaiementService(
            PaiementRepository paiementRepo,
            TicketRepository ticketRepo,
            NotificationService notificationService
    ) {
        this.paiementRepo = paiementRepo;
        this.ticketRepo = ticketRepo;
        this.notificationService = notificationService;
    }

    // PAYMENT
    public Paiement payer(
            Integer ticketId,
            MethodePayment methode
    ){

        Ticket t = ticketRepo.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found"
                        ));

        Paiement p = new Paiement();

        p.setTicket(t);

        p.setMontant(t.getMontant());

        p.setMethode(methode);

        if(methode == MethodePayment.ESPECES){

            p.setStatut(PaymentStatus.PAYE);

        } else {

            p.setStatut(PaymentStatus.EN_ATTENTE);
        }

        Paiement saved =
                paiementRepo.save(p);

        // AUTO NOTIFICATION
        notificationService.autoCreate(
                t.getUtilisateur(),
                "PAIEMENT",
                "Paiement réussi",
                "Votre paiement de "
                        + t.getMontant()
                        + " DH a été confirmé"
        );

        return saved;
    }

    // CONFIRM PAYMENT
    public Paiement confirmer(Integer paiementId){

        Paiement p = paiementRepo.findById(paiementId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Paiement not found"
                        ));

        p.setStatut(PaymentStatus.PAYE);

        return paiementRepo.save(p);
    }

    // PAYMENT SUMMARY
    public PaymentSummaryDTO summary(Integer ticketId){

        Ticket ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket introuvable"
                        ));

        PaymentSummaryDTO dto =
                new PaymentSummaryDTO();

        dto.montant = ticket.getMontant();

        dto.duree = ticket.getDuree();

        dto.parking = ticket.getPlace()
                .getParking()
                .getNom();

        dto.place = ticket.getPlace()
                .getNumero();

        return dto;
    }
}