package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.repository.utilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class utilisateurService {

    @Autowired
    private utilisateurRepository repository;

    public utilisateur save(utilisateur u){
        return repository.save(u);
    }

    public List<utilisateur> getAll(){
        return repository.findAll();
    }

    public utilisateur getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}