package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Parking;
import com.smartparking.parking_api.service.ParkingService;

import org.springframework.web.bind.annotation.*;

import java.util.List;


import com.smartparking.parking_api.dto.ParkingResponse;

@RestController
@RequestMapping("/api/parkings")
@CrossOrigin
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    public List<Parking> getAllParkings() {
        return parkingService.getAllParkings();
    }

    @GetMapping("/{id}")
    public Parking getParkingById(@PathVariable Integer id) {
        return parkingService.getParkingById(id);
    }

    @PostMapping
    public Parking createParking(@RequestBody Parking parking) {
        return parkingService.saveParking(parking);
    }

    @PutMapping("/{id}")
    public Parking updateParking(@PathVariable Integer id, @RequestBody Parking parking) {
        parking.setId(id);
        return parkingService.saveParking(parking);
    }

    @DeleteMapping("/{id}")
    public void deleteParking(@PathVariable Integer id) {
        parkingService.deleteParking(id);
    }

    @GetMapping("/byType")
    public List<Parking> getByType(@RequestParam String type){
        return parkingService.getByType(type);
    }

    @GetMapping("/nearby")
    public List<ParkingResponse> getNearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(required = false) String type
    ) {
        return parkingService.getNearbyParkings(lat, lng, type);
    }

    // TEST
    @GetMapping("/test")
    public String test(){
        return "API WORKING";
    }
}