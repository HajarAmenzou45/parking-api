package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Vehicule;
import com.smartparking.parking_api.enums.VehiculeType;
import com.smartparking.parking_api.service.VehiculeService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    private final VehiculeService service;

    public VehiculeController(VehiculeService service) {
        this.service = service;
    }

    // ✅ GET ALL
    @GetMapping
    public List<Vehicule> getAll() {
        return service.getAll();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public Vehicule getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    // ✅ CREATE
    @PostMapping
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return service.save(vehicule);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    //  TYPES (important)
    @GetMapping("/types")
    public VehiculeType[] getTypes() {
        return VehiculeType.values();
    }
}