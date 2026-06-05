package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.PaymentCard;
import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.repository.PaymentCardRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentCardService {

    private final PaymentCardRepository repository;
    private final utilisateurService utilisateurService;

    public PaymentCardService(
            PaymentCardRepository repository,
            utilisateurService utilisateurService
    ) {
        this.repository = repository;
        this.utilisateurService = utilisateurService;
    }

    public PaymentCard save(
            String email,
            PaymentCard card
    ){

        utilisateur user =
                utilisateurService.getCurrentUser(email);

        card.setUtilisateur(user);

        return repository.save(card);
    }

    public PaymentCard get(String email){

        return repository.findByUtilisateurEmail(email)
                .orElse(null);
    }

    public void delete(String email){

        PaymentCard card =
                repository.findByUtilisateurEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Carte introuvable"
                                ));

        repository.delete(card);
    }
}