package com.milan.reservation.services.impl;

import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Route;
import com.milan.reservation.model.Train;
import com.milan.reservation.model.TrainSchedule;
import com.milan.reservation.repository.RouteRepository;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.repository.TrainScheduleRepository;
import com.milan.reservation.responses.ClassResponse;
import com.milan.reservation.responses.TrainResponse;
import com.milan.reservation.services.ClassService;
import com.milan.reservation.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;
    private final TrainScheduleRepository trainScheduleRepository;
    private final ClassService classService;

    @Override
    public List<TrainResponse> searchTrains(String sourceStation, String destinationStation, LocalDate journeyDate) {
        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> hasRoute(train, sourceStation, destinationStation))
                .map(train -> mapToTrainResponse(train, sourceStation, destinationStation, journeyDate))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainResponse> getTrainsByRoute(String routeCode) {
        String[] stations = routeCode.split("-");
        if (stations.length != 2) {
            throw new IllegalArgumentException("Invalid route code format");
        }

        String sourceStation = stations[0];
        String destinationStation = stations[1];

        List<Train> trains = trainRepository.findAll();
        return trains.stream()
                .filter(train -> hasRoute(train, sourceStation, destinationStation))
                .map(train -> mapToTrainResponse(train, sourceStation, destinationStation, LocalDate.now()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public TrainResponse getTrainByNumber(Long trainNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(trainNumber);
        if (routes.isEmpty()) {
            return null;
        }

        Route firstRoute = routes.get(0);
        Route lastRoute = routes.get(routes.size() - 1);
        return mapToTrainResponse(train, firstRoute.getStation().getStationCode(),
                lastRoute.getStation().getStationCode(), LocalDate.now());
    }

    @Override
    public List<TrainResponse> getTrainsByStation(String stationCode, LocalDate date) {
        List<Long> trainNumbers = routeRepository.findTrainNumbersByStationCode(stationCode);
        List<Train> trains = trainRepository.findAllByNumberIn(trainNumbers);

        return trains.stream()
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    Route stationRoute = routes.stream()
                            .filter(route -> route.getStation().getStationCode().equals(stationCode))
                            .findFirst()
                            .orElse(null);

                    if (stationRoute == null) {
                        return null;
                    }

                    Route firstRoute = routes.get(0);
                    Route lastRoute = routes.get(routes.size() - 1);
                    return mapToTrainResponse(train, firstRoute.getStation().getStationCode(),
                            lastRoute.getStation().getStationCode(), date);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTrainSchedule(Long trainNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(trainNumber);
        return routes.stream()
                .map(route -> String.format("%s: Arrival-%s, Departure-%s, Platform-%s",
                        route.getStation().getStationCode(),
                        route.getArrivalTime(),
                        route.getDepartureTime(),
                        route.getPlatformNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isTrainRunningOnDate(Long trainNumber, LocalDate date) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Optional<TrainSchedule> schedule = trainScheduleRepository.findByTrainAndDate(train, date);
        return schedule.isPresent() && schedule.get().isActive() &&
                schedule.get().getRunningDays().contains(date.getDayOfWeek());
    }

    @Override
    public List<String> getIntermediateStations(Long trainNumber) {
        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(trainNumber);
        if (routes.size() <= 2) {
            return Collections.emptyList();
        }

        return routes.subList(1, routes.size() - 1).stream()
                .map(route -> route.getStation().getStationCode())
                .collect(Collectors.toList());
    }

    @Override
    public String getJourneyDuration(Long trainNumber, String sourceStation, String destinationStation) {
        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(trainNumber);
        Route sourceRoute = routes.stream()
                .filter(route -> route.getStation().getStationCode().equals(sourceStation))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Source station not found in route"));

        Route destinationRoute = routes.stream()
                .filter(route -> route.getStation().getStationCode().equals(destinationStation))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Destination station not found in route"));

        int durationInMinutes = calculateDuration(sourceRoute.getDepartureTime(), destinationRoute.getArrivalTime());
        return formatDuration(durationInMinutes);
    }

    @Override
    public List<TrainResponse> getConnectingTrains(String sourceStation, String destinationStation, LocalDate date) {
        List<Train> directTrains = trainRepository.findAll().stream()
                .filter(train -> hasRoute(train, sourceStation, destinationStation))
                .collect(Collectors.toList());

        List<TrainResponse> directResponses = directTrains.stream()
                .map(train -> mapToTrainResponse(train, sourceStation, destinationStation, date))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // TODO: Implement connecting trains logic
        return directResponses;
    }

    private TrainResponse mapToTrainResponse(Train train, String sourceStation, String destinationStation, LocalDate date) {
        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
        Route sourceRoute = routes.stream()
                .filter(route -> route.getStation().getStationCode().equals(sourceStation))
                .findFirst()
                .orElse(null);

        Route destinationRoute = routes.stream()
                .filter(route -> route.getStation().getStationCode().equals(destinationStation))
                .findFirst()
                .orElse(null);

        if (sourceRoute == null || destinationRoute == null) {
            return null;
        }

        List<ClassResponse> classAvailability = classService.getAvailableClasses(
                train.getNumber(), sourceStation, destinationStation);

        Optional<TrainSchedule> schedule = trainScheduleRepository.findByTrainAndDate(train, date);
        Set<String> runningDays = schedule.map(s -> s.getRunningDays().stream()
                .map(Enum::toString)
                .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        return TrainResponse.builder()
                .trainNumber(train.getNumber())
                .trainName(train.getName())
                .trainType(train.getType().getCode())
                .sourceStation(sourceStation)
                .destinationStation(destinationStation)
                .departureTime(LocalDateTime.of(date, sourceRoute.getDepartureTime()))
                .arrivalTime(LocalDateTime.of(date, destinationRoute.getArrivalTime()))
                .totalDuration(formatDuration(calculateDuration(sourceRoute.getDepartureTime(), destinationRoute.getArrivalTime())))
                .classAvailability(classAvailability)
                .hasWifi(train.isHasWifi())
                .hasPantryCar(train.isHasPantryCar())
                .isActive(train.isActive())
                .routeCode(sourceStation + "-" + destinationStation)
                .totalStops(countStops(routes, sourceRoute.getSequence(), destinationRoute.getSequence()))
                .intermediateStations(getIntermediateStations(routes, sourceRoute.getSequence(), destinationRoute.getSequence()))
                .runningDays(runningDays)
                .zoneCode(train.getZone() != null ? train.getZone().getZoneCode() : null)
                .stationSchedule(getStationSchedule(routes, date))
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

    private int calculateDuration(LocalTime departure, LocalTime arrival) {
        if (departure == null || arrival == null) {
            return 0;
        }

        int minutes = arrival.getHour() * 60 + arrival.getMinute() -
                (departure.getHour() * 60 + departure.getMinute());
        if (minutes < 0) {
            minutes += 24 * 60; // Add 24 hours if train arrives next day
        }
        return minutes;
    }

    private String formatDuration(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        
        if (hours > 0 && remainingMinutes > 0) {
            return hours + " hour" + (hours > 1 ? "s" : "") + " " + remainingMinutes + " min" + (remainingMinutes > 1 ? "s" : "");
        } else if (hours > 0) {
            return hours + " hour" + (hours > 1 ? "s" : "");
        } else {
            return remainingMinutes + " min" + (remainingMinutes > 1 ? "s" : "");
        }
    }

    private int countStops(List<Route> routes, int sourceSequence, int destinationSequence) {
        return (int) routes.stream()
                .sorted(Comparator.comparingInt(Route::getSequence))
                .filter(route -> route.getSequence() > sourceSequence && route.getSequence() < destinationSequence)
                .count();
    }

    private List<String> getIntermediateStations(List<Route> routes, int sourceSequence, int destinationSequence) {
        return routes.stream()
                .sorted(Comparator.comparingInt(Route::getSequence))
                .filter(route -> route.getSequence() > sourceSequence && route.getSequence() < destinationSequence)
                .map(route -> route.getStation().getStationCode())
                .collect(Collectors.toList());
    }

    private Map<String, LocalDateTime> getStationSchedule(List<Route> routes, LocalDate date) {
        Map<String, LocalDateTime> schedule = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order
        LocalDate currentDate = date;
        LocalTime previousTime = null;

        // Sort routes by sequence
        List<Route> sortedRoutes = routes.stream()
                .sorted(Comparator.comparingInt(Route::getSequence))
                .collect(Collectors.toList());

        for (Route route : sortedRoutes) {
            LocalTime time = route.getArrivalTime() != null ? route.getArrivalTime() : route.getDepartureTime();
            
            // If time is null, skip this station
            if (time == null) {
                continue;
            }

            // Check if we need to move to next day
            if (previousTime != null && time.isBefore(previousTime)) {
                currentDate = currentDate.plusDays(1);
            }

            schedule.put(route.getStation().getStationCode(), LocalDateTime.of(currentDate, time));
            previousTime = time;
        }

        return schedule;
    }
} 