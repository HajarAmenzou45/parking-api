package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.VehiculeType;
import com.smartparking.parking_api.repository.VehiculeTypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types")
public class VehiculeTypeController {

    private final VehiculeTypeRepository vehiculeTypeRepository;

    public VehiculeTypeController(VehiculeTypeRepository vehiculeTypeRepository) {
        this.vehiculeTypeRepository = vehiculeTypeRepository;
    }

    @GetMapping
    public List<VehiculeType> getTypes() {
        return vehiculeTypeRepository.findAll();
    }
}