package com.smartparking.parking_api.service;

import com.smartparking.parking_api.enums.StatutPlace;
import com.smartparking.parking_api.repository.PaiementRepository;
import com.smartparking.parking_api.repository.PlaceRepository;
import com.smartparking.parking_api.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final TicketRepository ticketRepository;
    private final PaiementRepository paiementRepository;
    private final PlaceRepository placeRepository;

    public DashboardService(TicketRepository ticketRepository,
                            PaiementRepository paiementRepository,
                            PlaceRepository placeRepository) {
        this.ticketRepository = ticketRepository;
        this.paiementRepository = paiementRepository;
        this.placeRepository = placeRepository;
    }

    public Map<String, Object> getStatsByParking(Long parkingId){

        long totalVehicules = ticketRepository.countCurrentVehicules(parkingId);

        Double revenue = paiementRepository.getTodayRevenueByParking(parkingId);
        double dailyRevenue = revenue != null ? revenue : 0;

        long availableSpots = placeRepository.countByParkingIdAndStatut(parkingId, StatutPlace.LIBRE);

        long totalSpots = placeRepository.countByParkingId(parkingId);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalVehicules", totalVehicules);
        stats.put("dailyRevenue", dailyRevenue);
        stats.put("availableSpots", availableSpots);
        stats.put("totalSpots", totalSpots);

        return stats;
    }
}