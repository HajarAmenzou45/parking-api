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
    private final NotificationService notificationService;

    public TicketService(
            TicketRepository ticketRepo,
            PlaceRepository placeRepo,
            utilisateurService userService,
            NotificationService notificationService
    ) {
        this.ticketRepo = ticketRepo;
        this.placeRepo = placeRepo;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    // ENTRY
    public Ticket entrer(
            Long placeId,
            String email
    ){

        Place place = placeRepo.findById(placeId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Place not found"
                        ));

        if(place.getStatut() != StatutPlace.RESERVEE){

            throw new RuntimeException(
                    "Place doit être réservée"
            );
        }

        utilisateur user =
                userService.getCurrentUser(email);

        // UPDATE PLACE STATUS
        place.setStatut(StatutPlace.OCCUPEE);

        placeRepo.save(place);

        // CREATE TICKET
        Ticket t = new Ticket();

        t.setPlace(place);

        t.setUtilisateur(user);

        t.setHeureEntree(LocalDateTime.now());

        t.setStatut(StatutTicket.OUVERT);

        // AUTO NOTIFICATION
        notificationService.autoCreate(
                user,
                "TICKET",
                "Ticket généré",
                "Votre ticket pour la place "
                        + place.getNumero()
                        + " a été créé"
        );

        return ticketRepo.save(t);
    }

    // EXIT + CALCUL
    public Ticket sortir(Integer ticketId){

        Ticket t = ticketRepo.findById(ticketId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found"
                        ));

        t.setHeureSortie(LocalDateTime.now());

        long minutes =
                Duration.between(
                        t.getHeureEntree(),
                        t.getHeureSortie()
                ).toMinutes();

        double heures =
                Math.ceil(minutes / 60.0);

        t.setDuree(heures);

        double prix =
                t.getPlace()
                        .getParking()
                        .getPrixParHeure();

        t.setMontant(heures * prix);

        t.setStatut(StatutTicket.FERME);

        // LIBERER PLACE
        Place p = t.getPlace();

        p.setStatut(StatutPlace.LIBRE);

        placeRepo.save(p);

        return ticketRepo.save(t);
    }

    // ACTIVE TICKET
    public Ticket getActiveTicket(String email){

        return ticketRepo
                .findByUtilisateurEmailAndStatut(
                        email,
                        StatutTicket.OUVERT
                )
                .orElse(null);
    }
    public Ticket getById(Integer id){

        return ticketRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Ticket not found"
                        ));
    }
}