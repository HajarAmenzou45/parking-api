package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Notification;
import com.smartparking.parking_api.entity.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUtilisateurOrderByDateCreationDesc(
            utilisateur utilisateur
    );
}