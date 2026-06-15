package com.smartparking.parking_api.entity;

import com.smartparking.parking_api.enums.StatutTicket;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime heureEntree;
    private LocalDateTime heureSortie;

    private Double duree; // en heures
    private Double montant;

    @Enumerated(EnumType.STRING)
    private StatutTicket statut;

    @ManyToOne
    private utilisateur utilisateur;

    @ManyToOne
    private Place place;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;
}