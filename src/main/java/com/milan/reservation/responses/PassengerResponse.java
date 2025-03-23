package com.milan.reservation.responses;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class PassengerResponse {

    private Long id;

    private String name;

    private Integer age;

    private String gender;

    private String coachNumber;

    private String seatNumber;

    private Boolean isLeadPassenger;

    private String berthPreference;

    private String seatType;

    private String status;

    private String ticketNumber;

    private String idProofType;

    private String idProofNumber;

    private String mealPreference;

    private Boolean requiresAssistance;

    private String specialRequests;
} 