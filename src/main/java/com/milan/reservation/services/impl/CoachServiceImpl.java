package com.milan.reservation.services.impl;

import com.milan.reservation.enums.SeatStatus;
import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Class;
import com.milan.reservation.model.Coach;
import com.milan.reservation.model.Seat;
import com.milan.reservation.model.Train;
import com.milan.reservation.repository.ClassRepository;
import com.milan.reservation.repository.CoachRepository;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.responses.CoachResponse;
import com.milan.reservation.responses.SeatResponse;
import com.milan.reservation.services.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final TrainRepository trainRepository;
    private final ClassRepository classRepository;

    @Override
    public List<CoachResponse> getCoachesByClass(Long trainNumber, String classCode) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Class trainClass = classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found: " + classCode));

        List<Coach> coaches = coachRepository.findAllByTrainNumber(trainNumber);
        return coaches.stream()
                .filter(coach -> coach.getTrainClass().equals(trainClass))
                .map(this::mapToCoachResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CoachResponse> getAllCoaches(Long trainNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        List<Coach> coaches = coachRepository.findAllByTrainNumber(trainNumber);
        return coaches.stream()
                .map(this::mapToCoachResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CoachResponse getCoachByNumber(Long trainNumber, String coachNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Coach coach = coachRepository.findByCoachNumberAndTrain(coachNumber, train)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found: " + coachNumber));

        return mapToCoachResponse(coach);
    }

    @Override
    public Integer getAvailableSeats(Long trainNumber, String coachNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Coach coach = coachRepository.findByCoachNumberAndTrain(coachNumber, train)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found: " + coachNumber));

        return (int) coach.getSeats().stream()
                .filter(seat -> seat.getStatus() == SeatStatus.AVAILABLE)
                .count();
    }

    @Override
    public List<String> getAvailableBerths(Long trainNumber, String coachNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Coach coach = coachRepository.findByCoachNumberAndTrain(coachNumber, train)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found: " + coachNumber));

        return coach.getSeats().stream()
                .filter(seat -> seat.getStatus() == SeatStatus.AVAILABLE)
                .map(Seat::getSeatNumber)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isCoachAvailable(Long trainNumber, String coachNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Coach coach = coachRepository.findByCoachNumberAndTrain(coachNumber, train)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found: " + coachNumber));

        return coach.getSeats().stream()
                .anyMatch(seat -> seat.getStatus() == SeatStatus.AVAILABLE);
    }

    @Override
    public List<String> getCoachFacilities(Long trainNumber, String coachNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Coach coach = coachRepository.findByCoachNumberAndTrain(coachNumber, train)
                .orElseThrow(() -> new ResourceNotFoundException("Coach not found: " + coachNumber));

        List<String> facilities = new ArrayList<>();
        if (coach.isHasWifi()) facilities.add("WiFi");
        if (coach.isHasPantry()) facilities.add("Pantry");
        if (coach.isHasChargingPoint()) facilities.add("Charging Point");
        if (coach.isHasReadingLight()) facilities.add("Reading Light");
        if (coach.isAc()) facilities.add("AC");
        return facilities;
    }

    @Override
    public Map<String, Integer> getCoachOccupancy(Long trainNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        List<Coach> coaches = coachRepository.findAllByTrainNumber(trainNumber);
        Map<String, Integer> occupancy = new HashMap<>();

        coaches.forEach(coach -> {
            int totalSeats = coach.getTotalSeats();
            int availableSeats = (int) coach.getSeats().stream()
                    .filter(seat -> seat.getStatus() == SeatStatus.AVAILABLE)
                    .count();
            int occupancyPercentage = (int) ((totalSeats - availableSeats) * 100.0 / totalSeats);
            occupancy.put(coach.getCoachNumber(), occupancyPercentage);
        });

        return occupancy;
    }

    private CoachResponse mapToCoachResponse(Coach coach) {
        return CoachResponse.builder()
                .coachNumber(coach.getCoachNumber())
                .classCode(coach.getTrainClass().getClassCode())
                .className(coach.getTrainClass().getClassName())
                .totalSeats(coach.getTotalSeats())
                .availableSeats(getAvailableSeats(coach.getTrain().getNumber(), coach.getCoachNumber()))
                .occupiedSeats(coach.getTotalSeats() - getAvailableSeats(coach.getTrain().getNumber(), coach.getCoachNumber()))
                .availableBerths(getAvailableBerths(coach.getTrain().getNumber(), coach.getCoachNumber()))
                .isActive(true) // This should be determined based on maintenance status
                .position(String.valueOf(coach.getSequence()))
                .facilities(getCoachFacilities(coach.getTrain().getNumber(), coach.getCoachNumber()))
                .hasWifi(coach.isHasWifi())
                .hasPantry(coach.isHasPantry())
                .hasCharger(coach.isHasChargingPoint())
                .hasReadingLight(coach.isHasReadingLight())
                .status("ACTIVE") // This should be determined based on maintenance status
                .remarks(coach.getMaintenanceDueDate() != null ? "Maintenance due on " + coach.getMaintenanceDueDate() : null)
                .build();
    }

    private SeatResponse mapToSeatResponse(Seat seat) {
        return SeatResponse.builder()
                .seatNumber(seat.getSeatNumber())
                .status(seat.getStatus())
                .isWindow(seat.isWindow())
                .isAisle(seat.isAisle())
                .rowNumber(seat.getRowNumber())
                .build();
    }
} 