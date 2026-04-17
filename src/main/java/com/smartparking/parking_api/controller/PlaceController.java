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

    // ✅ GET ALL + FILTER BY PARKING (optional)
    @GetMapping
    public List<Place> getPlaces(@RequestParam(required = false) Long parkingId){

        if(parkingId != null){
            return placeService.getByParking(parkingId);
        }

        return placeService.getAll();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public Place getPlaceById(@PathVariable Long id){
        return placeService.getPlaceById(id);
    }

    // ✅ CREATE
    @PostMapping
    public Place create(@RequestBody Place place){
        return placeService.save(place);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Place updatePlace(@PathVariable Long id, @RequestBody Place place){

        Place existing = placeService.getPlaceById(id);

        if(existing == null){
            throw new RuntimeException("Place not found with id = " + id);
        }

        place.setId(id);

        return placeService.save(place);
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        placeService.delete(id);
    }

    //  OPTIONAL (clean endpoint)
    @GetMapping("/byParking/{parkingId}")
    public List<Place> getByParking(@PathVariable Long parkingId){
        return placeService.getByParking(parkingId);
    }
}