package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.PaymentCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentCardRepository
        extends JpaRepository<PaymentCard, Long> {

    Optional<PaymentCard> findByUtilisateurEmail(String email);
}