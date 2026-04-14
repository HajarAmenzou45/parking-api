package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Parking;
import com.smartparking.parking_api.service.ParkingService;
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

    // ✅ GET ALL + FILTER (FIXED + DEBUG)
    @GetMapping
    public List<Parking> getParkings(@RequestParam(name = "type", required = false) String type) {

        System.out.println("TYPE RECEIVED = " + type); // 🔥 debug مهم

        if(type != null && !type.isEmpty()){
            return parkingService.getByType(type.trim());
        }

        return parkingService.getAllParkings();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public Parking getParkingById(@PathVariable Integer id) {
        return parkingService.getParkingById(id);
    }

    // ✅ CREATE
    @PostMapping
    public Parking createParking(@RequestBody Parking parking) {
        return parkingService.saveParking(parking);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Parking updateParking(@PathVariable Integer id, @RequestBody Parking parking) {
        parking.setId(id);
        return parkingService.saveParking(parking);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void deleteParking(@PathVariable Integer id) {
        parkingService.deleteParking(id);
    }

    // ✅ NEARBY + FILTER
    @GetMapping("/nearby")
    public List<ParkingResponse> getNearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(name = "type", required = false) String type
    ) {
        return parkingService.getNearbyParkings(lat, lng, type);
    }

    // ✅ TEST
    @GetMapping("/test")
    public String test(){
        return "API WORKING";
    }
}