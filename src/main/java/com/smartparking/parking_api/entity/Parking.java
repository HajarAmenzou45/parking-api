package com.smartparking.parking_api.entity;

import com.smartparking.parking_api.enums.VehiculeType;
import jakarta.persistence.*;
import lombok.*;

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

    //  TYPE
    @Enumerated(EnumType.STRING)
    private VehiculeType typeVehicule;
}