package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.dto.PaymentSummaryDTO;
import com.smartparking.parking_api.entity.Paiement;
import com.smartparking.parking_api.enums.MethodePayment;
import com.smartparking.parking_api.service.PaiementService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    private final PaiementService service;

    public PaiementController(PaiementService service) {
        this.service = service;
    }

    @PostMapping("/pay/{ticketId}")
    public Paiement payer(@PathVariable Integer ticketId,
                          @RequestParam MethodePayment methode){
        return service.payer(ticketId, methode);
    }

    @PutMapping("/confirm/{id}")
    public Paiement confirmer(@PathVariable Integer id){
        return service.confirmer(id);
    }

    @GetMapping("/summary")
    public PaymentSummaryDTO summary(
            @RequestParam Integer ticketId
    ){
        return service.summary(ticketId);
    }
}