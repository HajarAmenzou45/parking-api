package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.Paiement;
import com.smartparking.parking_api.entity.Ticket;
import com.smartparking.parking_api.enums.MethodePayment;
import com.smartparking.parking_api.enums.PaymentStatus;
import com.smartparking.parking_api.enums.StatutTicket;
import com.smartparking.parking_api.repository.PaiementRepository;
import com.smartparking.parking_api.repository.TicketRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaiementService {

    private final PaiementRepository repository;
    private final TicketRepository ticketRepository;

    public PaiementService(PaiementRepository repository,
                           TicketRepository ticketRepository) {
        this.repository = repository;
        this.ticketRepository = ticketRepository;
    }

    // 🔹 GET ALL
    public List<Paiement> getAll(){
        return repository.findAll();
    }

    // 🔹 GET BY ID
    public Paiement getPaiementById(Integer id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement not found"));
    }

    // 🔹 DELETE
    public void delete(Integer id){
        repository.deleteById(id);
    }

    // 💰 LOGIC PAYMENT (FIXED 🔥)
    public Paiement pay(Integer ticketId, MethodePayment method){

        Ticket ticket = ticketRepository.findById(ticketId.longValue())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // ❌ خاص ticket يكون مسدود
        if(ticket.getStatut() != StatutTicket.FERME){
            throw new RuntimeException("Ticket must be closed first");
        }

        // ❌ إلى راه تخلص قبل
        if(ticket.getPaiement() != null){
            throw new RuntimeException("Already paid");
        }

        Paiement paiement = new Paiement();
        paiement.setTicket(ticket);
        paiement.setMontant(ticket.getMontant());
        paiement.setMethod(method);

        // 🔥 logic حسب method
        if(method == MethodePayment.EN_LIGNE){
            paiement.setStatut(PaymentStatus.EN_ATTENTE);
        } else {
            paiement.setStatut(PaymentStatus.PAYE);
        }

        // 🔥🔥 أهم حاجة (حل المشكل ديالك)
        ticket.setPaiement(paiement);

        // save
        repository.save(paiement);
        ticketRepository.save(ticket);

        return paiement;
    }

    // 🔹 SAVE (optional)
    public Paiement save(Paiement paiement){
        return repository.save(paiement);
    }
}