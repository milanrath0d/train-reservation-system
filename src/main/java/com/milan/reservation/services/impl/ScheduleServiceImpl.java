package com.milan.reservation.services.impl;

import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Route;
import com.milan.reservation.model.Train;
import com.milan.reservation.model.TrainSchedule;
import com.milan.reservation.repository.RouteRepository;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.repository.TrainScheduleRepository;
import com.milan.reservation.requests.UpdateScheduleRequest;
import com.milan.reservation.responses.ScheduleResponse;
import com.milan.reservation.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ScheduleService} for managing train schedules
 *
 * @author Milan Rathod
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final TrainRepository trainRepository;
    private final TrainScheduleRepository trainScheduleRepository;
    private final RouteRepository routeRepository;

    @Override
    public ScheduleResponse getTrainSchedule(Long trainNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(trainNumber);
        Optional<TrainSchedule> schedule = trainScheduleRepository.findByTrainAndDate(train, LocalDate.now());

        return createScheduleResponse(train, routes, schedule.orElse(null));
    }

    @Override
    public ScheduleResponse getTrainScheduleByDate(Long trainNumber, LocalDate date) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(trainNumber);
        Optional<TrainSchedule> schedule = trainScheduleRepository.findByTrainAndDate(train, date);

        return createScheduleResponse(train, routes, schedule.orElse(null));
    }

    @Override
    public List<ScheduleResponse> getStationSchedule(String stationCode, LocalDate date) {
        List<Long> trainNumbers = routeRepository.findTrainNumbersByStationCode(stationCode);
        List<Train> trains = trainRepository.findAllByNumberIn(trainNumbers);

        return trains.stream()
                .map(train -> {
                    List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber());
                    Optional<TrainSchedule> schedule = trainScheduleRepository.findByTrainAndDate(train, date);
                    return createScheduleResponse(train, routes, schedule.orElse(null));
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ScheduleResponse updateTrainSchedule(Long trainNumber, UpdateScheduleRequest request) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        TrainSchedule schedule = trainScheduleRepository.findByTrainAndDate(train, LocalDate.now())
                .orElse(TrainSchedule.builder().train(train).build());

        schedule.setRunningDays(request.getRunningDays().stream()
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toSet()));
        schedule.setActive(true);

        trainScheduleRepository.save(schedule);

        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(trainNumber);
        return createScheduleResponse(train, routes, schedule);
    }

    @Override
    public List<ScheduleResponse> getTrainDelayHistory(Long trainNumber, LocalDate fromDate, LocalDate toDate) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        List<Route> routes = routeRepository.findAllByTrainNumberOrderBySequence(trainNumber);
        List<ScheduleResponse> delayHistory = new ArrayList<>();

        // TODO: Implement delay history logic
        return delayHistory;
    }

    @Override
    public List<String> getTrainRunningDays(Long trainNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Optional<TrainSchedule> schedule = trainScheduleRepository.findByTrainAndDate(train, LocalDate.now());
        return schedule.map(s -> s.getRunningDays().stream()
                .map(DayOfWeek::toString)
                .collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    @Override
    public LocalDate getNextAvailableDate(Long trainNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Optional<TrainSchedule> schedule = trainScheduleRepository.findByTrainAndDate(train, LocalDate.now());
        if (schedule.isEmpty() || !schedule.get().isActive()) {
            return null;
        }

        LocalDate currentDate = LocalDate.now();
        while (!schedule.get().getRunningDays().contains(currentDate.getDayOfWeek())) {
            currentDate = currentDate.plusDays(1);
        }

        return currentDate;
    }

    private ScheduleResponse createScheduleResponse(Train train, List<Route> routes, TrainSchedule schedule) {
        ScheduleResponse response = new ScheduleResponse();
        response.setTrainNumber(train.getNumber());
        response.setTrainName(train.getName());
        response.setActive(schedule != null && schedule.isActive());
        response.setRunningDays(schedule != null ? schedule.getRunningDays().stream()
                .map(DayOfWeek::toString)
                .collect(Collectors.toList()) : new ArrayList<>());

        List<ScheduleResponse.StationSchedule> stationSchedules = routes.stream()
                .map(route -> {
                    ScheduleResponse.StationSchedule stationSchedule = new ScheduleResponse.StationSchedule();
                    stationSchedule.setStationCode(route.getStation().getStationCode());
                    stationSchedule.setStationName(route.getStation().getStationName());
                    if (route.getArrivalTime() != null) {
                        stationSchedule.setArrivalTime(LocalDateTime.of(LocalDate.now(), route.getArrivalTime()));
                    }
                    if (route.getDepartureTime() != null) {
                        stationSchedule.setDepartureTime(LocalDateTime.of(LocalDate.now(), route.getDepartureTime()));
                    }
                    stationSchedule.setPlatformNumber(Integer.parseInt(route.getPlatformNumber()));
                    stationSchedule.setDistanceFromSource(route.getDistanceFromOrigin());
                    stationSchedule.setHaltTime(calculateHaltTime(route));
                    return stationSchedule;
                })
                .collect(Collectors.toList());

        response.setStations(stationSchedules);
        response.setLastUpdated(LocalDateTime.now());

        return response;
    }

    private int calculateHaltTime(Route route) {
        if (route.getArrivalTime() == null || route.getDepartureTime() == null) {
            return 0;
        }
        return (route.getDepartureTime().getHour() * 60 + route.getDepartureTime().getMinute()) -
                (route.getArrivalTime().getHour() * 60 + route.getArrivalTime().getMinute());
    }
} 