package com.milan.reservation.service;

import com.milan.reservation.model.Route;
import com.milan.reservation.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Milan Rathod
 */
@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findAllByStationCode(String stationCode) {
        return routeRepository.findAllByStationCode(stationCode);
    }

    public List<Long> findTrainCodesByStationCode(String stationCode) {
        return routeRepository.findTrainNumbersByStationCode(stationCode);
    }

    public List<Route> findRouteByTrainNumber(Long trainNumber) {
        return routeRepository
            .findAllByTrainNumberOrderBySequence(trainNumber);
    }
}
