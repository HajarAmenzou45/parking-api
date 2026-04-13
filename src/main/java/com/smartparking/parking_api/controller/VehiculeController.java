package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Vehicule;
import com.smartparking.parking_api.entity.VehiculeType;
import com.smartparking.parking_api.service.VehiculeService;
import com.smartparking.parking_api.service.VehiculeTypeService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    private final VehiculeService service;
    private final VehiculeTypeService typeService; //

    public VehiculeController(VehiculeService service, VehiculeTypeService typeService) {
        this.service = service;
        this.typeService = typeService;
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

    // TYPES (correct)
    @GetMapping("/types")
    public List<VehiculeType> getTypes() {
        return typeService.getAllTypes();
    }
}