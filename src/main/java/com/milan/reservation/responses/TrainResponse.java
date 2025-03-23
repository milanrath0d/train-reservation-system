package com.milan.reservation.responses;

import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * @author Milan Rathod
 */
@Data
@Builder
public class TrainResponse {

    private Long trainNumber;

    private String trainName;

    private String trainType;

    private String sourceStation;

    private String destinationStation;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private String totalDuration;

    private List<ClassResponse> classAvailability;

    private Boolean hasWifi;

    private Boolean hasPantryCar;

    private Boolean isActive;

    private String routeCode;

    private Integer totalStops;

    private List<String> intermediateStations;

    private Set<String> runningDays;

    private String zoneCode;

    private String remarks;

    private Map<String, LocalDateTime> stationSchedule;
}
