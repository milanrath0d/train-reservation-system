package com.milan.reservation.requests;

import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class PassengerRequest {

    @NotBlank(message = "Passenger name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 120, message = "Age cannot exceed 120")
    private Integer age;

    @NotBlank(message = "Gender is required")
    private String gender;

    private boolean isLeadPassenger;

    private String berthPreference;

    private String idProofType;

    private String idProofNumber;

    private String mealPreference;

    private String specialRequests;
} 