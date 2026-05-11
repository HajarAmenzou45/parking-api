package com.smartparking.parking_api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartparking.parking_api.enums.RoleUtilisateur;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String motDePasse;

    private String nomComplet;

    @Column(unique = true)
    private String username;

    private String telephone;

    private String genre;

    private String photoProfil;

    private String carteBancaire;

    @Enumerated(EnumType.STRING)
    private RoleUtilisateur role;
}