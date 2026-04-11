package com.smartparking.parking_api.entity;

import com.smartparking.parking_api.enums.StatutPlace;
import com.smartparking.parking_api.enums.TypePlace;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer niveau;

    private String numero;

    @Enumerated(EnumType.STRING)
    private StatutPlace statut;

    @Enumerated(EnumType.STRING)
    private TypePlace type;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

}