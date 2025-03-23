package com.milan.reservation.requests;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request class for adding/updating a station
 *
 * @author Milan Rathod
 */
@Data
public class AddStationRequest {

    @NotBlank(message = "Station name is required")
    private String stationName;

    @NotBlank(message = "Station code is required")
    private String stationCode;

    @NotBlank(message = "Zone code is required")
    private String zoneCode;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    private String address;

    @NotNull(message = "Number of platforms is required")
    @Min(value = 1, message = "Number of platforms must be at least 1")
    private Integer numberOfPlatforms;

    private Double latitude;

    private Double longitude;

    private String contactNumber;

    @Email(message = "Invalid email format")
    private String email;
} 