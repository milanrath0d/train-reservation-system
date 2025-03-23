package com.milan.reservation.requests;

import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
public class BookingRequest {

    @NotNull(message = "User id is required")
    private String userId;

    @NotNull(message = "Train number is required")
    private Long trainNumber;

    @NotBlank(message = "Class code is required")
    private String classCode;

    @NotBlank(message = "Source station is required")
    private String sourceStation;

    @NotBlank(message = "Destination station is required")
    private String destinationStation;

    @NotNull(message = "Journey date is required")
    private LocalDate journeyDate;

    @NotNull(message = "Passengers list cannot be empty")
    @Size(min = 1, message = "At least one passenger is required")
    @Valid
    private List<PassengerRequest> passengers;

    private String boardingPoint;

    private String bookingSource;

    private String paymentMethod;

    private Map<String, String> preferences;
} 