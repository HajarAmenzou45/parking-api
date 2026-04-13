package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.VehiculeType;
import com.smartparking.parking_api.repository.VehiculeTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeTypeService {

    private final VehiculeTypeRepository repository;

    public VehiculeTypeService(VehiculeTypeRepository repository) {
        this.repository = repository;
    }

    public List<VehiculeType> getAllTypes() {
        return repository.findAll();
    }
}