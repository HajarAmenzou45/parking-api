package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.dto.AdminStatsDTO;
import com.smartparking.parking_api.service.AdminService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping("/stats")
    public AdminStatsDTO stats(){

        return service.getStats();
    }
}