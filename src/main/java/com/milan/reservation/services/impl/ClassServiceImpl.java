package com.milan.reservation.services.impl;

import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Class;
import com.milan.reservation.model.Coach;
import com.milan.reservation.model.SeatAvailability;
import com.milan.reservation.model.Train;
import com.milan.reservation.repository.ClassRepository;
import com.milan.reservation.repository.CoachRepository;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.responses.ClassResponse;
import com.milan.reservation.services.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final TrainRepository trainRepository;
    private final CoachRepository coachRepository;

    @Override
    public List<ClassResponse> getAllClasses() {
        return classRepository.findAll().stream()
                .map(this::mapToClassResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClassResponse getClassByCode(String classCode) {
        Class trainClass = classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with code: " + classCode));
        return mapToClassResponse(trainClass);
    }

    @Override
    public List<ClassResponse> getClassesByTrain(Long trainNumber) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        List<Coach> coaches = coachRepository.findAllByTrainNumber(trainNumber);
        return coaches.stream()
                .map(Coach::getTrainClass)
                .distinct()
                .map(this::mapToClassResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassResponse> getAvailableClasses(Long trainNumber, String sourceStation, String destinationStation) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        List<Coach> coaches = coachRepository.findAllByTrainNumber(trainNumber);
        LocalDate today = LocalDate.now();

        return coaches.stream()
                .map(Coach::getTrainClass)
                .map(trainClass -> {
                    ClassResponse response = mapToClassResponse(trainClass);
                    // Get seat availability for this class
                    SeatAvailability availability = trainClass.getSeatAvailabilities().stream()
                            .filter(sa -> sa.getTrain().equals(train) && sa.getTravelDate().equals(today))
                            .findFirst()
                            .orElse(null);

                    if (availability != null) {
                        response.setAvailableSeats(availability.getAvailableSeats());
                        response.setWaitingListSize(availability.getWaitingList());
                    }
                    return response;
                })
                .filter(classResponse -> classResponse.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isClassAvailable(Long trainNumber, String classCode) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Class trainClass = classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with code: " + classCode));

        LocalDate today = LocalDate.now();
        return trainClass.getSeatAvailabilities().stream()
                .filter(sa -> sa.getTrain().equals(train) && sa.getTravelDate().equals(today))
                .findFirst()
                .map(sa -> sa.getAvailableSeats() > 0)
                .orElse(false);
    }

    @Override
    public Integer getAvailableSeats(Long trainNumber, String classCode) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Class trainClass = classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with code: " + classCode));

        LocalDate today = LocalDate.now();
        return trainClass.getSeatAvailabilities().stream()
                .filter(sa -> sa.getTrain().equals(train) && sa.getTravelDate().equals(today))
                .findFirst()
                .map(SeatAvailability::getAvailableSeats)
                .orElse(0);
    }

    @Override
    public Integer getWaitingListCount(Long trainNumber, String classCode) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));

        Class trainClass = classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with code: " + classCode));

        LocalDate today = LocalDate.now();
        return trainClass.getSeatAvailabilities().stream()
                .filter(sa -> sa.getTrain().equals(train) && sa.getTravelDate().equals(today))
                .findFirst()
                .map(SeatAvailability::getWaitingList)
                .orElse(0);
    }

    @Override
    public List<String> getClassFacilities(String classCode) {
        Class trainClass = classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with code: " + classCode));

        List<String> facilities = new ArrayList<>();
        // Add basic facilities based on class type
        if (classCode.equals("1A") || classCode.equals("2A") || classCode.equals("3A") || classCode.equals("CC")) {
            facilities.add("Air Conditioning");
            facilities.add("Reading Light");
            facilities.add("Mobile Charging Point");
            facilities.add("Blanket");
            facilities.add("Pillow");
        }

        if (classCode.equals("1A")) {
            facilities.add("Individual LCD Screen");
            facilities.add("Premium Bedding");
            facilities.add("Foot Massage");
            facilities.add("Personal Attendant");
        } else if (classCode.equals("2A") || classCode.equals("3A")) {
            facilities.add("Curtains");
            facilities.add("Basic Bedding");
        }

        return facilities;
    }

    private ClassResponse mapToClassResponse(Class trainClass) {
        return ClassResponse.builder()
                .classCode(trainClass.getClassCode())
                .className(trainClass.getClassName())
                .description(getClassDescription(trainClass.getClassCode()))
                .isAC(isAcClass(trainClass.getClassCode()))
                .hasWifi(hasWifi(trainClass.getClassCode()))
                .hasPantry(hasPantry(trainClass.getClassCode()))
                .hasCharger(hasCharger(trainClass.getClassCode()))
                .hasReadingLight(hasReadingLight(trainClass.getClassCode()))
                .facilities(getClassFacilities(trainClass.getClassCode()))
                .isActive(trainClass.isActive())
                .build();
    }

    private String getClassDescription(String classCode) {
        switch (classCode) {
            case "1A":
                return "First Class AC - Luxury 2-Berth/4-Berth Compartments";
            case "2A":
                return "Second Class AC - 2-Tier Sleeper";
            case "3A":
                return "Third Class AC - 3-Tier Sleeper";
            case "SL":
                return "Sleeper Class - Non-AC";
            case "CC":
                return "AC Chair Car";
            case "2S":
                return "Second Sitting - Non-AC";
            default:
                return "Standard Class";
        }
    }

    private boolean isAcClass(String classCode) {
        return classCode.equals("1A") || classCode.equals("2A") || 
               classCode.equals("3A") || classCode.equals("CC");
    }

    private boolean hasWifi(String classCode) {
        return classCode.equals("1A") || classCode.equals("2A");
    }

    private boolean hasPantry(String classCode) {
        return classCode.equals("1A");
    }

    private boolean hasCharger(String classCode) {
        return classCode.equals("1A") || classCode.equals("2A") || 
               classCode.equals("3A") || classCode.equals("CC");
    }

    private boolean hasReadingLight(String classCode) {
        return classCode.equals("1A") || classCode.equals("2A") || 
               classCode.equals("3A") || classCode.equals("CC");
    }
} 