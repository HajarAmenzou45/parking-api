package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.*;
import com.smartparking.parking_api.enums.*;
import com.smartparking.parking_api.repository.*;
import org.springframework.stereotype.Service;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepo;
    private final TicketRepository ticketRepo;

    public PaiementService(PaiementRepository paiementRepo, TicketRepository ticketRepo) {
        this.paiementRepo = paiementRepo;
        this.ticketRepo = ticketRepo;
    }

    public Paiement payer(Integer ticketId, MethodePayment methode){

        Ticket t = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        Paiement p = new Paiement();
        p.setTicket(t);
        p.setMontant(t.getMontant());
        p.setMethode(methode);

        if(methode == MethodePayment.ESPECES){
            p.setStatut(PaymentStatus.PAYE);
        } else {
            p.setStatut(PaymentStatus.EN_ATTENTE);
        }

        return paiementRepo.save(p);
    }

    public Paiement confirmer(Integer paiementId){

        Paiement p = paiementRepo.findById(paiementId)
                .orElseThrow(() -> new RuntimeException("Paiement not found"));

        p.setStatut(PaymentStatus.PAYE);

        return paiementRepo.save(p);
    }
}