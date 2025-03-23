package com.milan.reservation.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassAvailability {
    private String classCode;
    private String className;
    private int availableSeats;
    private int waitingList;
    private double fare;
    private boolean isAvailable;
} 