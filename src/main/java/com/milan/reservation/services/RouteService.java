package com.milan.reservation.services;

import com.milan.reservation.responses.RouteResponse;
import java.util.List;
import java.util.Map;

public interface RouteService {

    List<RouteResponse> getAllRoutes();

    RouteResponse getRouteByCode(String routeCode);

    List<RouteResponse> getRoutesByZone(String zoneCode);

    List<RouteResponse> searchRoutes(String sourceStation, String destinationStation);

    List<String> getRouteStations(String routeCode);

    Integer getRouteDistance(String routeCode);

    Integer getDistanceBetweenStations(String routeCode, String sourceStation, String destinationStation);

    List<RouteResponse> getAlternativeRoutes(String sourceStation, String destinationStation);

    Map<String, Integer> getStationDistances(String routeCode);
}
