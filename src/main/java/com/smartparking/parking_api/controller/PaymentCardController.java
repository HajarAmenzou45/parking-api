package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.PaymentCard;
import com.smartparking.parking_api.service.PaymentCardService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/payment-card")
public class PaymentCardController {

    private final PaymentCardService service;

    public PaymentCardController(
            PaymentCardService service
    ) {
        this.service = service;
    }

    @GetMapping
    public PaymentCard getCard(
            Authentication auth
    ){

        return service.get(
                auth.getName()
        );
    }

    @PostMapping
    public PaymentCard addCard(
            Authentication auth,
            @RequestBody PaymentCard card
    ){

        return service.save(
                auth.getName(),
                card
        );
    }

    @DeleteMapping
    public String deleteCard(
            Authentication auth
    ){

        service.delete(
                auth.getName()
        );

        return "Carte supprimée";
    }
}