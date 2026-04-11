package com.smartparking.parking_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom;
    private String adresse;
    private Integer nombrePlaces;
    private Integer placesDisponibles;
    private Double latitude;
    private Double longitude;
    private Double prixParHeure;

    // 🔥 MULTI TYPE
    @ManyToMany
    @JoinTable(
            name = "parking_vehicle_type",
            joinColumns = @JoinColumn(name = "parking_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicule_type_id")
    )
    private List<VehiculeType> types;
}