package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Ticket;
import com.smartparking.parking_api.service.TicketService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @PostMapping("/entry")
    public Ticket entry(@RequestParam Long placeId, Authentication auth){
        return service.entrer(placeId, auth.getName());
    }

    @PutMapping("/exit/{id}")
    public Ticket exit(@PathVariable Integer id){
        return service.sortir(id);
    }
}