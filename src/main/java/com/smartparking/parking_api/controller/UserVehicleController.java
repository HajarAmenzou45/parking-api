package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Vehicule;
import com.smartparking.parking_api.service.VehiculeService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/vehicles")
public class UserVehicleController {

    private final VehiculeService service;

    public UserVehicleController(VehiculeService service) {
        this.service = service;
    }

    // GET USER VEHICLES
    @GetMapping
    public List<Vehicule> getVehicles(Authentication auth){

        return service.getUserVehicles(auth.getName());
    }

    // ADD VEHICLE
    @PostMapping
    public Vehicule addVehicle(
            Authentication auth,
            @RequestBody Vehicule vehicule
    ){

        return service.addVehicle(
                auth.getName(),
                vehicule
        );
    }

    // DELETE VEHICLE
    @DeleteMapping("/{id}")
    public void deleteVehicle(
            Authentication auth,
            @PathVariable Long id
    ){

        service.deleteVehicle(
                auth.getName(),
                id
        );
    }
}