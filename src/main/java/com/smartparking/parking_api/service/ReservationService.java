package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.*;

import com.smartparking.parking_api.enums.StatutPlace;
import com.smartparking.parking_api.enums.StatutReservation;

import com.smartparking.parking_api.repository.PlaceRepository;
import com.smartparking.parking_api.repository.ReservationRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepo;
    private final PlaceRepository placeRepo;
    private final utilisateurService userService;
    private final NotificationService notificationService;

    public ReservationService(
            ReservationRepository reservationRepo,
            PlaceRepository placeRepo,
            utilisateurService userService,
            NotificationService notificationService
    ) {
        this.reservationRepo = reservationRepo;
        this.placeRepo = placeRepo;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public List<Reservation> getAll(){

        return reservationRepo.findAll();
    }

    public Reservation getReservationById(Integer id){

        return reservationRepo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Reservation not found"
                        ));
    }

    public void delete(Integer id){

        reservationRepo.deleteById(id);
    }

    // RESERVATION LOGIC
    public Reservation reserver(
            Long placeId,
            String email
    ){

        Place place = placeRepo.findById(placeId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Place not found"
                        ));

        if(place.getStatut() != StatutPlace.LIBRE){

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Place non disponible"
            );
        }

        utilisateur user =
                userService.getCurrentUser(email);

        // UPDATE PLACE STATUS
        place.setStatut(StatutPlace.RESERVEE);

        placeRepo.save(place);

        // CREATE RESERVATION
        Reservation r = new Reservation();

        r.setPlace(place);

        r.setUtilisateur(user);

        r.setDateDebut(LocalDateTime.now());

        r.setStatut(StatutReservation.ACTIVE);

        // AUTO NOTIFICATION
        notificationService.autoCreate(
                user,
                "RESERVATION",
                "Réservation confirmée",
                "Votre place "
                        + place.getNumero()
                        + " a été réservée avec succès"
        );

        return reservationRepo.save(r);
    }
}