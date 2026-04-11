package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Paiement;
import com.smartparking.parking_api.enums.PaymentStatus;
import com.smartparking.parking_api.service.PaiementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.smartparking.parking_api.enums.MethodePayment;
@RestController
@RequestMapping("/api/paiements")
@CrossOrigin
public class PaiementController {

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    // 🔹 GET ALL
    @GetMapping
    public List<Paiement> getAll(){
        return paiementService.getAll();
    }

    // 🔹 GET BY ID
    @GetMapping("/{id}")
    public Paiement getById(@PathVariable Integer id){
        return paiementService.getPaiementById(id);
    }

    // PAYMENT
    @PostMapping("/pay/{ticketId}")
    public Paiement pay(@PathVariable Integer ticketId,
                        @RequestParam MethodePayment method){
        return paiementService.pay(ticketId, method);
    }

    @PutMapping("/confirm/{id}")
    public Paiement confirm(@PathVariable Integer id){

        Paiement paiement = paiementService.getPaiementById(id);

        paiement.setStatut(PaymentStatus.PAYE);

        return paiementService.save(paiement);
    }

    // 🔹 DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        paiementService.delete(id);
    }
}