package com.milan.reservation.services.impl;

import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Class;
import com.milan.reservation.model.Train;
import com.milan.reservation.model.TrainFare;
import com.milan.reservation.repository.ClassRepository;
import com.milan.reservation.repository.TrainFareRepository;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.requests.UpdateFareRequest;
import com.milan.reservation.responses.FareResponse;
import com.milan.reservation.services.TrainFareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrainFareServiceImpl implements TrainFareService {

    private final TrainFareRepository trainFareRepository;
    private final TrainRepository trainRepository;
    private final ClassRepository classRepository;

    @Override
    public List<FareResponse> getTrainFares(Long trainNumber, String classCode) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Class trainClass = classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with code: " + classCode));

        List<TrainFare> fares = trainFareRepository.findByTrainAndTrainClass(train, trainClass);
        return fares.stream()
                .map(this::mapToFareResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FareResponse> getRouteFares(String sourceStation, String destinationStation, String classCode) {
        Class trainClass = classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with code: " + classCode));

        List<TrainFare> fares = trainFareRepository.findByFromStationAndToStationAndTrainClass(
                sourceStation, destinationStation, trainClass);
        return fares.stream()
                .map(this::mapToFareResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<FareResponse> updateTrainFares(Long trainNumber, UpdateFareRequest request) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Class trainClass = classRepository.findByClassCode(request.getClassCode())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with code: " + request.getClassCode()));

        List<TrainFare> fares = trainFareRepository.findByTrainAndTrainClass(train, trainClass);
        LocalDateTime effectiveFrom = LocalDate.parse(request.getEffectiveFrom(), DateTimeFormatter.ISO_DATE)
                .atStartOfDay();

        fares.forEach(fare -> {
            fare.setBaseFare(request.getBaseFare().doubleValue());
            if (request.getDynamicFareMultiplier() != null) {
                fare.setDynamicFareMultiplier(request.getDynamicFareMultiplier().doubleValue());
            }
            fare.setDynamicPricingEnabled(request.getIsDiscountApplicable() != null && request.getIsDiscountApplicable());
        });

        List<TrainFare> updatedFares = trainFareRepository.saveAll(fares);
        return updatedFares.stream()
                .map(this::mapToFareResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void bulkUpdateFares(List<UpdateFareRequest> requests) {
        requests.forEach(request -> {
            Train train = trainRepository.findByNumber(request.getTrainNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + request.getTrainNumber()));

            Class trainClass = classRepository.findByClassCode(request.getClassCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found with code: " + request.getClassCode()));

            List<TrainFare> fares = trainFareRepository.findByTrainAndTrainClass(train, trainClass);
            LocalDateTime effectiveFrom = LocalDate.parse(request.getEffectiveFrom(), DateTimeFormatter.ISO_DATE)
                    .atStartOfDay();

            fares.forEach(fare -> {
                fare.setBaseFare(request.getBaseFare().doubleValue());
                if (request.getDynamicFareMultiplier() != null) {
                    fare.setDynamicFareMultiplier(request.getDynamicFareMultiplier().doubleValue());
                }
                fare.setDynamicPricingEnabled(request.getIsDiscountApplicable() != null && request.getIsDiscountApplicable());
            });

            trainFareRepository.saveAll(fares);
        });
    }

    private FareResponse mapToFareResponse(TrainFare fare) {
        return FareResponse.builder()
                .trainNumber(fare.getTrain().getNumber())
                .classCode(fare.getTrainClass().getClassCode())
                .className(fare.getTrainClass().getClassName())
                .fromStation(fare.getFromStation())
                .toStation(fare.getToStation())
                .distanceInKm(fare.getDistanceInKm())
                .baseFare(BigDecimal.valueOf(fare.getBaseFare()))
                .dynamicFareMultiplier(BigDecimal.valueOf(fare.getDynamicFareMultiplier()))
                .isDynamicPricingEnabled(fare.isDynamicPricingEnabled())
                .routeCode(fare.getFromStation() + "-" + fare.getToStation())
                .build();
    }
} 