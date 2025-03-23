package com.milan.reservation.services.impl;

import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Route;
import com.milan.reservation.model.Station;
import com.milan.reservation.model.Train;
import com.milan.reservation.repository.RouteRepository;
import com.milan.reservation.repository.StationRepository;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.responses.RouteResponse;
import com.milan.reservation.services.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;

    @Override
    public List<RouteResponse> getAllRoutes() {
        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    return mapToRouteResponse(train, routes);
                })
                .collect(Collectors.toList());
    }

    @Override
    public RouteResponse getRouteByCode(String routeCode) {
        String[] stations = routeCode.split("-");
        if (stations.length != 2) {
            throw new IllegalArgumentException("Invalid route code format");
        }

        String sourceStation = stations[0];
        String destinationStation = stations[1];

        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> hasRoute(train, sourceStation, destinationStation))
                .findFirst()
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    return mapToRouteResponse(train, routes);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Route not found: " + routeCode));
    }

    @Override
    public List<RouteResponse> getRoutesByZone(String zoneCode) {
        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> train.getZone() != null && train.getZone().getZoneCode().equals(zoneCode))
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    return mapToRouteResponse(train, routes);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<RouteResponse> searchRoutes(String sourceStation, String destinationStation) {
        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> hasRoute(train, sourceStation, destinationStation))
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    return mapToRouteResponse(train, routes);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRouteStations(String routeCode) {
        String[] stations = routeCode.split("-");
        if (stations.length != 2) {
            throw new IllegalArgumentException("Invalid route code format");
        }

        String sourceStation = stations[0];
        String destinationStation = stations[1];

        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> hasRoute(train, sourceStation, destinationStation))
                .findFirst()
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    return routes.stream()
                            .map(route -> route.getStation().getStationCode())
                            .collect(Collectors.toList());
                })
                .orElseThrow(() -> new ResourceNotFoundException("Route not found: " + routeCode));
    }

    @Override
    public Integer getRouteDistance(String routeCode) {
        String[] stations = routeCode.split("-");
        if (stations.length != 2) {
            throw new IllegalArgumentException("Invalid route code format");
        }

        String sourceStation = stations[0];
        String destinationStation = stations[1];

        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> hasRoute(train, sourceStation, destinationStation))
                .findFirst()
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    return calculateDistance(routes, sourceStation, destinationStation);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Route not found: " + routeCode));
    }

    @Override
    public Integer getDistanceBetweenStations(String routeCode, String sourceStation, String destinationStation) {
        String[] stations = routeCode.split("-");
        if (stations.length != 2) {
            throw new IllegalArgumentException("Invalid route code format");
        }

        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> hasRoute(train, stations[0], stations[1]))
                .findFirst()
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    return calculateDistance(routes, sourceStation, destinationStation);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Route not found: " + routeCode));
    }

    @Override
    public List<RouteResponse> getAlternativeRoutes(String sourceStation, String destinationStation) {
        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> hasRoute(train, sourceStation, destinationStation))
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    return mapToRouteResponse(train, routes);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> getStationDistances(String routeCode) {
        String[] stations = routeCode.split("-");
        if (stations.length != 2) {
            throw new IllegalArgumentException("Invalid route code format");
        }

        String sourceStation = stations[0];
        String destinationStation = stations[1];

        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> hasRoute(train, sourceStation, destinationStation))
                .findFirst()
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    Map<String, Integer> distances = new HashMap<>();
                    for (Route route : routes) {
                        distances.put(route.getStation().getStationCode(), route.getDistanceFromOrigin());
                    }
                    return distances;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Route not found: " + routeCode));
    }

    private RouteResponse mapToRouteResponse(Train train, List<Route> routes) {
        if (routes.isEmpty()) {
            return null;
        }

        Route firstRoute = routes.get(0);
        Route lastRoute = routes.get(routes.size() - 1);

        return RouteResponse.builder()
                .routeCode(firstRoute.getStation().getStationCode() + "-" + lastRoute.getStation().getStationCode())
                .routeName(train.getName())
                .sourceStation(firstRoute.getStation().getStationCode())
                .destinationStation(lastRoute.getStation().getStationCode())
                .totalDistance(lastRoute.getDistanceFromOrigin())
                .totalStations(routes.size())
                .stations(routes.stream()
                        .map(route -> route.getStation().getStationCode())
                        .collect(Collectors.toList()))
                .stationDistances(routes.stream()
                        .collect(Collectors.toMap(
                                route -> route.getStation().getStationCode(),
                                Route::getDistanceFromOrigin)))
                .zoneCode(train.getZone() != null ? train.getZone().getZoneCode() : null)
                .isActive(train.isActive())
                .isCircular(isCircularRoute(routes))
                .majorStations(getMajorStations(routes))
                .routeType(train.getType().getCode())
                .build();
    }

    private boolean hasRoute(Train train, String sourceStation, String destinationStation) {
        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
        if (routes.isEmpty()) {
            return false;
        }

        boolean foundSource = false;
        boolean foundDestination = false;

        for (Route route : routes) {
            if (route.getStation().getStationCode().equals(sourceStation)) {
                foundSource = true;
            }
            if (route.getStation().getStationCode().equals(destinationStation)) {
                foundDestination = true;
            }
            if (foundSource && foundDestination) {
                return true;
            }
        }

        return false;
    }

    private int calculateDistance(List<Route> routes, String sourceStation, String destinationStation) {
        int sourceDistance = -1;
        int destinationDistance = -1;

        for (Route route : routes) {
            if (route.getStation().getStationCode().equals(sourceStation)) {
                sourceDistance = route.getDistanceFromOrigin();
            }
            if (route.getStation().getStationCode().equals(destinationStation)) {
                destinationDistance = route.getDistanceFromOrigin();
            }
            if (sourceDistance != -1 && destinationDistance != -1) {
                break;
            }
        }

        if (sourceDistance == -1 || destinationDistance == -1) {
            throw new IllegalArgumentException("Invalid stations for this route");
        }

        return Math.abs(destinationDistance - sourceDistance);
    }

    private boolean isCircularRoute(List<Route> routes) {
        if (routes.size() < 2) {
            return false;
        }
        Route firstRoute = routes.get(0);
        Route lastRoute = routes.get(routes.size() - 1);
        return firstRoute.getStation().getStationCode().equals(lastRoute.getStation().getStationCode());
    }

    private List<String> getMajorStations(List<Route> routes) {
        return routes.stream()
                .filter(route -> route.getStation().getNumberOfPlatforms() >= 5)
                .map(route -> route.getStation().getStationCode())
                .collect(Collectors.toList());
    }
} 