package com.smartparking.parking_api.repository;

import com.smartparking.parking_api.entity.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifRepository extends JpaRepository<Tarif, Integer> {
}