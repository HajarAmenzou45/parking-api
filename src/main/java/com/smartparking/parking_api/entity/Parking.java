package com.smartparking.parking_api.entity;

import com.smartparking.parking_api.entity.VehiculeType;
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

    // MULTI TYPES
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "parking_vehicle_type",
            joinColumns = @JoinColumn(name = "parking_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicule_type_id")
    )
    private List<VehiculeType> types;
}