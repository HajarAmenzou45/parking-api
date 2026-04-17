package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/parking/{id}")
    public Map<String, Object> getStats(@PathVariable Long id){
        return dashboardService.getStatsByParking(id);
    }

}