package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Ticket;
import com.smartparking.parking_api.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    // ================= GET ALL =================
    @GetMapping
    public List<Ticket> getAll() {
        return service.getAll();
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public Ticket getById(@PathVariable Long id) {
        return service.getTicketById(id);
    }

    // ================= ENTRY =================
    @PostMapping("/entry")
    public Ticket entry(@RequestParam Long vehiculeId,
                        @RequestParam Long placeId) {
        return service.createEntry(vehiculeId, placeId);
    }

    // ================= EXIT =================
    @PutMapping("/exit/{id}")
    public Ticket exit(@PathVariable Long id) {
        return service.exit(id);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {

        Ticket existing = service.getTicketById(id);

        ticket.setId(id);

        return service.save(ticket);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}