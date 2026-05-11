package com.smartparking.parking_api.entity;

import com.smartparking.parking_api.enums.NotificationStatus;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String titre;

    private String message;

    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    private NotificationStatus statut;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private utilisateur utilisateur;
}