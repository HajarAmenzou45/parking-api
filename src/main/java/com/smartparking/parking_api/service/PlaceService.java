package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.Parking;
import com.smartparking.parking_api.entity.Place;
import com.smartparking.parking_api.repository.ParkingRepository;
import com.smartparking.parking_api.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {

    private final PlaceRepository repository;
    private final ParkingRepository parkingRepository;

    public PlaceService(PlaceRepository repository,
                        ParkingRepository parkingRepository) {
        this.repository = repository;
        this.parkingRepository = parkingRepository;
    }

    public List<Place> getAll(){
        return repository.findAll();
    }

    public Place save(Place place) {

        if (place.getParking() == null) {
            throw new RuntimeException("Parking obligatoire");
        }

        Integer parkingId = place.getParking().getId();

        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("Parking not found"));

        //   object
        place.setParking(parking);

        return repository.save(place);
    }


    public Place getPlaceById(Long id){
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public List<Place> getByParking(Long parkingId){
        return repository.findByParkingId(parkingId);
    }
}