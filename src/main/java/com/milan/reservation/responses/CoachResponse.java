package com.milan.reservation.responses;

import lombok.Data;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class CoachResponse {

    private String coachNumber;

    private String classCode;

    private String className;

    private Integer totalSeats;

    private Integer availableSeats;

    private Integer occupiedSeats;

    private List<String> availableBerths;

    private Map<String, Boolean> seatAvailability;

    private Boolean isActive;

    private String position;

    private List<String> facilities;

    private Boolean hasWifi;

    private Boolean hasPantry;

    private Boolean hasCharger;

    private Boolean hasReadingLight;

    private String status;

    private String remarks;
} 