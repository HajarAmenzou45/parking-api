package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Reservation;
import com.smartparking.parking_api.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
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
    public Reservation create(@RequestBody Reservation reservation){
        return service.save(reservation);
    }

    @PutMapping("/{id}")
    public Reservation update(@PathVariable Integer id, @RequestBody Reservation reservation){

        Reservation existing = service.getReservationById(id);

        if(existing == null){
            throw new RuntimeException("Reservation not found with id = " + id);
        }

        reservation.setId(id);

        return service.save(reservation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }
}