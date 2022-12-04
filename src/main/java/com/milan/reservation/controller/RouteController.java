package com.milan.reservation.controller;

import com.milan.reservation.response.RouteInfoResponse;
import com.milan.reservation.service.RouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public List<RouteInfoResponse> findRouteInfoByTrainNumber(@RequestParam Long trainNumber) {
        return routeService.findRouteByTrainNumber(trainNumber)
            .stream()
            .map(route -> RouteInfoResponse.builder()
                .sequence(route.getSequence())
                .stationName(route.getStationName())
                .stationCode(route.getStationCode())
                .distanceFromOrigin(route.getDistanceFromOrigin())
                .arrivalTime(route.getArrivalTime() != null ? route.getArrivalTime().toString() : null)
                .halt(route.getHalt())
                .build())
            .collect(Collectors.toList());
    }
}
