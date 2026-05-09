package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Reservation;
import com.smartparking.parking_api.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reservation> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Integer id){
        return service.getReservationById(id);
    }

    @PostMapping
    public Reservation reserve(@RequestParam Long placeId, Authentication auth){

        String email = auth.getName();

        return service.reserver(placeId, email);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }
}
