package com.smartparking.parking_api.entity;

import com.smartparking.parking_api.enums.RoleUtilisateur;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String motDePasse;

    private String nomComplet;

    @Enumerated(EnumType.STRING)
    private RoleUtilisateur role;

}