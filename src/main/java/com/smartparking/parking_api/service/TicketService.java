package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.Ticket;
import com.smartparking.parking_api.entity.Vehicule;
import com.smartparking.parking_api.entity.Place;
import com.smartparking.parking_api.enums.StatutPlace;
import com.smartparking.parking_api.enums.StatutTicket;
import com.smartparking.parking_api.repository.TicketRepository;
import com.smartparking.parking_api.repository.VehiculeRepository;
import com.smartparking.parking_api.repository.PlaceRepository;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repository;
    private final VehiculeRepository vehiculeRepository;
    private final PlaceRepository placeRepository;

    public TicketService(TicketRepository repository,
                         VehiculeRepository vehiculeRepository,
                         PlaceRepository placeRepository) {
        this.repository = repository;
        this.vehiculeRepository = vehiculeRepository;
        this.placeRepository = placeRepository;
    }

    // GET ALL
    public List<Ticket> getAll() {
        return repository.findAll();
    }

    // SAVE
    public Ticket save(Ticket ticket) {
        return repository.save(ticket);
    }

    // GET BY ID
    public Ticket getTicketById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    // ENTRY 🚗
    public Ticket createEntry(Long vehiculeId, Long placeId) {

        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found"));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found"));

        if (place.getStatut() == StatutPlace.OCCUPEE) {
            throw new RuntimeException("Place already occupied");
        }

        place.setStatut(StatutPlace.OCCUPEE);
        placeRepository.save(place);

        Ticket ticket = new Ticket();
        ticket.setVehicule(vehicule);
        ticket.setPlace(place);
        ticket.setHeureEntree(LocalDateTime.now());
        ticket.setStatut(StatutTicket.OUVERT);

        return repository.save(ticket);
    }

    // EXIT 🚗 (FIXED)
    public Ticket exit(Long id) {

        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (ticket.getStatut() == StatutTicket.FERME) {
            throw new RuntimeException("Ticket already closed");
        }

        // time
        ticket.setHeureSortie(LocalDateTime.now());

        Duration duration = Duration.between(
                ticket.getHeureEntree(),
                ticket.getHeureSortie()
        );

        double hours = duration.toMinutes() / 60.0;
        if (hours < 1) hours = 1;

        // price (safe)
        double pricePerHour = 3.0;

        if (ticket.getPlace() != null && ticket.getPlace().getParking() != null) {
            pricePerHour = ticket.getPlace().getParking().getPrixParHeure();
        }

        double montant = hours * pricePerHour;

        // type (safe)
        if (ticket.getVehicule() != null && ticket.getVehicule().getType() != null) {

            String type = ticket.getVehicule().getType().getType();

            if ("TRUCK".equals(type)) {
                montant *= 2;
            } else if ("BIKE".equals(type)) {
                montant *= 0.5;
            } else if ("EV".equals(type)) {
                montant *= 0.8;
            }
        }

        ticket.setMontant(montant);
        ticket.setStatut(StatutTicket.FERME);

        // free place
        if (ticket.getPlace() != null) {
            Place place = ticket.getPlace();
            place.setStatut(StatutPlace.LIBRE);
            placeRepository.save(place);
        }

        return repository.save(ticket);
    }

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }
}