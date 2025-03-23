package com.milan.reservation.response;

import com.milan.reservation.enums.TrainType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Map;

@Data
@Builder
public class TrainAvailabilityResponse {
    private Long trainNumber;
    private String trainName;
    private TrainType trainType;
    private String sourceStation;
    private String destinationStation;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int totalDuration;
    private Map<String, ClassAvailability> classAvailability;  // Key: classCode
    private boolean hasWifi;
    private boolean hasPantryCar;
} 