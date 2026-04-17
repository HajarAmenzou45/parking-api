package com.smartparking.parking_api.entity;

import com.smartparking.parking_api.enums.StatutTicket;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime heureEntree;
    private LocalDateTime heureSortie;

    private Double montant;

    @Enumerated(EnumType.STRING)
    private StatutTicket statut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Paiement paiement;

   
}