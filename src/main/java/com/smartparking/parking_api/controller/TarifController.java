package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Tarif;
import com.smartparking.parking_api.service.TarifService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifs")
public class TarifController {

    private final TarifService service;

    public TarifController(TarifService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tarif> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Tarif getById(@PathVariable Integer id){
        return service.getTarifById(id);
    }

    @PostMapping
    public Tarif create(@RequestBody Tarif tarif) {
        return service.save(tarif);
    }

    @PutMapping("/{id}")
    public Tarif updateTarif(@PathVariable Integer id, @RequestBody Tarif tarif){

        Tarif existing = service.getTarifById(id);

        if(existing == null){
            throw new RuntimeException("Tarif not found with id = " + id);
        }

        tarif.setId(id);

        return service.save(tarif);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}