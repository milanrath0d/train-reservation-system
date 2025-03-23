package com.milan.reservation.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Response class for station details
 *
 * @author Milan Rathod
 */
@Data
@Builder
public class StationResponse {
    private String stationCode;
    private String stationName;
    private String city;
    private String state;
    private String address;
    private Integer numberOfPlatforms;
    private Double latitude;
    private Double longitude;
    private String zoneCode;
    private String contactNumber;
    private String email;
    private List<String> facilities;
    private boolean isActive;
} 