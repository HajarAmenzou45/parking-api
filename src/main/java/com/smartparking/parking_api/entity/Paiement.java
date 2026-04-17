package com.smartparking.parking_api.entity;

import com.smartparking.parking_api.enums.MethodePayment;
import com.smartparking.parking_api.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private MethodePayment method;

    private Double montant;

    @Enumerated(EnumType.STRING)
    private PaymentStatus statut;

    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}