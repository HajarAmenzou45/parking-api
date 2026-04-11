package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.Reservation;
import com.smartparking.parking_api.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> getAll(){
        return repository.findAll();
    }

    public Reservation save(Reservation reservation){
        return repository.save(reservation);
    }

    public Reservation getReservationById(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }

}