package com.milan.reservation.controllers;

import com.milan.reservation.responses.RouteResponse;
import com.milan.reservation.services.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @GetMapping("/{routeCode}")
    public ResponseEntity<RouteResponse> getRouteByCode(@PathVariable String routeCode) {
        return ResponseEntity.ok(routeService.getRouteByCode(routeCode));
    }

    @GetMapping("/zone/{zoneCode}")
    public ResponseEntity<List<RouteResponse>> getRoutesByZone(@PathVariable String zoneCode) {
        return ResponseEntity.ok(routeService.getRoutesByZone(zoneCode));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RouteResponse>> searchRoutes(
            @RequestParam String sourceStation,
            @RequestParam String destinationStation) {
        return ResponseEntity.ok(routeService.searchRoutes(sourceStation, destinationStation));
    }

    @GetMapping("/{routeCode}/stations")
    public ResponseEntity<List<String>> getRouteStations(@PathVariable String routeCode) {
        return ResponseEntity.ok(routeService.getRouteStations(routeCode));
    }

    @GetMapping("/{routeCode}/distance")
    public ResponseEntity<Integer> getRouteDistance(@PathVariable String routeCode) {
        return ResponseEntity.ok(routeService.getRouteDistance(routeCode));
    }

    @GetMapping("/{routeCode}/distance/between")
    public ResponseEntity<Integer> getDistanceBetweenStations(
            @PathVariable String routeCode,
            @RequestParam String sourceStation,
            @RequestParam String destinationStation) {
        return ResponseEntity.ok(routeService.getDistanceBetweenStations(routeCode, sourceStation, destinationStation));
    }

    @GetMapping("/alternatives")
    public ResponseEntity<List<RouteResponse>> getAlternativeRoutes(
            @RequestParam String sourceStation,
            @RequestParam String destinationStation) {
        return ResponseEntity.ok(routeService.getAlternativeRoutes(sourceStation, destinationStation));
    }

    @GetMapping("/{routeCode}/distances")
    public ResponseEntity<Map<String, Integer>> getStationDistances(@PathVariable String routeCode) {
        return ResponseEntity.ok(routeService.getStationDistances(routeCode));
    }
}
