package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.utilisateur;
import com.smartparking.parking_api.service.utilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class utilisateurController {

    @Autowired
    private utilisateurService service;

    @PostMapping
    public utilisateur create(@RequestBody utilisateur utilisateur){
        return service.save(utilisateur);
    }

    @GetMapping
    public List<utilisateur> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public utilisateur getById(@PathVariable Long id){
        return service.getById(id);
    }


    @PutMapping("/{id}")
    public utilisateur update(@PathVariable Long id,@RequestBody utilisateur utilisateur){
        utilisateur.setId(id);
        return service.save(utilisateur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }


}