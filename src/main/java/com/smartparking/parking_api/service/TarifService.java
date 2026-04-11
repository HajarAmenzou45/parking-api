package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.Tarif;
import com.smartparking.parking_api.repository.TarifRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifService {

    private final TarifRepository repository;

    public TarifService(TarifRepository repository) {
        this.repository = repository;
    }

    public List<Tarif> getAll(){
        return repository.findAll();
    }

    public Tarif save(Tarif tarif){
        return repository.save(tarif);
    }

    public Tarif getTarifById(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }
}