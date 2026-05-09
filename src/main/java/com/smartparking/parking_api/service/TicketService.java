package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.*;
import com.smartparking.parking_api.enums.StatutPlace;
import com.smartparking.parking_api.enums.StatutTicket;
import com.smartparking.parking_api.repository.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TicketService {

    private final TicketRepository ticketRepo;
    private final PlaceRepository placeRepo;
    private final utilisateurService userService;

    public TicketService(TicketRepository ticketRepo,
                         PlaceRepository placeRepo,
                         utilisateurService userService) {
        this.ticketRepo = ticketRepo;
        this.placeRepo = placeRepo;
        this.userService = userService;
    }

    // 🚗 ENTRY
    public Ticket entrer(Long placeId, String email){

        Place place = placeRepo.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        if(place.getStatut() != StatutPlace.RESERVEE){
            throw new RuntimeException("Place doit être réservée");
        }

        utilisateur user = userService.getCurrentUser(email);

        place.setStatut(StatutPlace.OCCUPEE);
        placeRepo.save(place);

        Ticket t = new Ticket();
        t.setPlace(place);
        t.setUtilisateur(user);
        t.setHeureEntree(LocalDateTime.now());
        t.setStatut(StatutTicket.OUVERT);

        return ticketRepo.save(t);
    }

    // 🚪 EXIT + CALCUL
    public Ticket sortir(Integer ticketId){

        Ticket t = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        t.setHeureSortie(LocalDateTime.now());

        long minutes = Duration.between(t.getHeureEntree(), t.getHeureSortie()).toMinutes();
        double heures = Math.ceil(minutes / 60.0);

        t.setDuree(heures);

        double prix = t.getPlace().getParking().getPrixParHeure();
        t.setMontant(heures * prix);

        t.setStatut(StatutTicket.FERME);

        // libérer la place
        Place p = t.getPlace();
        p.setStatut(StatutPlace.LIBRE);
        placeRepo.save(p);

        return ticketRepo.save(t);
    }
}