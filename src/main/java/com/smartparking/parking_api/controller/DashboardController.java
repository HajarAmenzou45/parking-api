package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/{parkingId}")
    public Map<String, Object> getStats(@PathVariable Long parkingId){
        return service.getStatsByParking(parkingId);
    }
}