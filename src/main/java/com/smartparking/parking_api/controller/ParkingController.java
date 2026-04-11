package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Parking;
import com.smartparking.parking_api.service.ParkingService;
import com.smartparking.parking_api.enums.VehiculeType;
import com.smartparking.parking_api.dto.ParkingResponse;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parkings")
@CrossOrigin
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    // ================= GET ALL =================
    @GetMapping
    public List<Parking> getAllParkings() {
        return parkingService.getAllParkings();
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public Parking getParkingById(@PathVariable Integer id) {
        return parkingService.getParkingById(id);
    }

    // ================= CREATE =================
    @PostMapping
    public Parking createParking(@RequestBody Parking parking) {
        return parkingService.saveParking(parking);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public Parking updateParking(@PathVariable Integer id, @RequestBody Parking parking) {

        Parking existing = parkingService.getParkingById(id);

        if (existing == null) {
            throw new RuntimeException("Parking not found with id = " + id);
        }

        parking.setId(id);

        return parkingService.saveParking(parking);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public void deleteParking(@PathVariable Integer id) {
        parkingService.deleteParking(id);
    }

    // =================  NEARBY =================
    @GetMapping("/nearby")
    public List<ParkingResponse> getNearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(required = false) String type) {

        return parkingService.getNearbyParkings(lat, lng, type);
    }
    @GetMapping("/byType")
    public List<Parking> getByType(@RequestParam String type){
        return parkingService.getByType(type);
    }

}