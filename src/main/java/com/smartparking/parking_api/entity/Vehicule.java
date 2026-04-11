package com.smartparking.parking_api.entity;

import com.smartparking.parking_api.enums.VehiculeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String marque;

    private String modele;

    @Column(name = "numeroPlaque", unique = true)
    private String numeroPlaque;

    // relation avec utilisateur
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur_id")
    @JsonIgnoreProperties({"vehicules"}) // باش ما يكونش boucle
    private utilisateur utilisateur;

    // enum type
    @Enumerated(EnumType.STRING)
    private VehiculeType type;

}