package com.smartparking.parking_api.service;

import com.smartparking.parking_api.entity.Parking;
import com.smartparking.parking_api.dto.ParkingResponse;
import com.smartparking.parking_api.repository.ParkingRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Comparator;
import java.util.Map;

@Service
public class ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Value("${ors.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // ================= CRUD =================

    public List<Parking> getAllParkings() {
        return parkingRepository.findAll();
    }

    public Parking saveParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    public Parking getParkingById(Integer id) {
        return parkingRepository.findById(id).orElse(null);
    }

    public void deleteParking(Integer id) {
        parkingRepository.deleteById(id);
    }

    // ================= FILTER BY TYPE 🔥 =================

    public List<Parking> getByType(String type){
        return parkingRepository.findByType(type);
    }

    // ================= NEARBY 🔥 =================

    public List<ParkingResponse> getNearbyParkings(double lat, double lng, String type) {

        List<Parking> parkings;

        if(type != null){
            parkings = parkingRepository.findByType(type);
        } else {
            parkings = parkingRepository.findAll();
        }

        return parkings.stream()

                .filter(p -> haversine(lat, lng, p.getLatitude(), p.getLongitude()) < 10)

                .map(p -> {
                    double distance = getRealDistance(lat, lng, p.getLatitude(), p.getLongitude());
                    return new ParkingResponse(p, distance);
                })

                .sorted(Comparator.comparing(ParkingResponse::getDistance))

                .limit(5)

                .toList();
    }

    // ================= ORS =================

    public double getRealDistance(double lat1, double lng1, double lat2, double lng2) {
        try {
            String url = "https://api.openrouteservice.org/v2/directions/driving-car"
                    + "?api_key=" + apiKey
                    + "&start=" + lng1 + "," + lat1
                    + "&end=" + lng2 + "," + lat2;

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            List<Map<String, Object>> features = (List<Map<String, Object>>) response.get("features");

            Map<String, Object> feature = features.get(0);
            Map<String, Object> properties = (Map<String, Object>) feature.get("properties");
            Map<String, Object> summary = (Map<String, Object>) properties.get("summary");

            double distanceMeters = ((Number) summary.get("distance")).doubleValue();

            return distanceMeters / 1000;

        } catch (Exception e) {
            return haversine(lat1, lng1, lat2, lng2);
        }
    }

    // ================= HAVERSINE =================

    private double haversine(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}