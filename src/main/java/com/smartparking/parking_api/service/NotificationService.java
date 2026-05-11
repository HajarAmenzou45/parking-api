package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.Notification;
import com.smartparking.parking_api.entity.utilisateur;

import com.smartparking.parking_api.enums.NotificationStatus;

import com.smartparking.parking_api.repository.NotificationRepository;
import com.smartparking.parking_api.repository.utilisateurRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final utilisateurRepository utilisateurRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            utilisateurRepository utilisateurRepository
    ) {
        this.notificationRepository = notificationRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // GET USER NOTIFICATIONS
    public List<Notification> getUserNotifications(String email){

        utilisateur user = utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return notificationRepository
                .findByUtilisateurOrderByDateCreationDesc(user);
    }

    // CREATE NOTIFICATION
    public Notification createNotification(
            String email,
            Notification notification
    ){

        utilisateur user = utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        notification.setUtilisateur(user);

        notification.setDateCreation(
                LocalDateTime.now()
        );

        notification.setStatut(
                NotificationStatus.NON_LU
        );

        return notificationRepository.save(notification);
    }

    // MARK AS READ
    public Notification markAsRead(Long id){

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Notification not found"
                                ));

        notification.setStatut(NotificationStatus.LU);

        return notificationRepository.save(notification);
    }

    // MARK ALL AS READ
    public void markAllAsRead(String email){

        utilisateur user = utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Notification> notifications =
                notificationRepository
                        .findByUtilisateurOrderByDateCreationDesc(user);

        for(Notification n : notifications){

            n.setStatut(NotificationStatus.LU);

            notificationRepository.save(n);
        }
    }

    // DELETE ALL
    public void deleteAll(String email){

        utilisateur user = utilisateurRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Notification> notifications =
                notificationRepository
                        .findByUtilisateurOrderByDateCreationDesc(user);

        notificationRepository.deleteAll(notifications);
    }
}