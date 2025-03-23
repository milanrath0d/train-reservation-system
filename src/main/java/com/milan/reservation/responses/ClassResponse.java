package com.milan.reservation.responses;

import lombok.Data;
import lombok.Builder;

import java.util.List;

@Data
@Builder
public class ClassResponse {

    private String classCode;

    private String className;

    private String description;

    private Integer totalSeats;

    private Integer availableSeats;

    private Integer waitingListSize;

    private Integer maxWaitingList;

    private Boolean isAC;

    private Boolean hasWifi;

    private Boolean hasPantry;

    private Boolean hasCharger;

    private Boolean hasReadingLight;

    private List<String> facilities;

    private Boolean isActive;

    private String remarks;
} 