package com.smartparking.parking_api.controller;

import com.smartparking.parking_api.entity.Place;
import com.smartparking.parking_api.service.PlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public List<Place> getAllPlaces(){
        return placeService.getAll();
    }

    @PostMapping
    public Place create(@RequestBody Place place){
        return placeService.save(place);
    }

    @PutMapping("/{id}")
    public Place updatePlace(@PathVariable Long id, @RequestBody Place place){

        Place existing = placeService.getPlaceById(id);

        if(existing == null){
            throw new RuntimeException("Place not found with id = " + id);
        }

        place.setId(id);

        return placeService.save(place);
    }

    @GetMapping("/{id}")
    public Place getPlaceById(@PathVariable Long id){
        return placeService.getPlaceById(id);
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        placeService.delete(id);
    }

    @GetMapping("/byParking/{parkingId}")
    public List<Place> getByParking(@PathVariable Long parkingId){
        return placeService.getByParking(parkingId);
    }
}






