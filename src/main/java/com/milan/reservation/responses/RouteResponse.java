package com.milan.reservation.responses;

import lombok.Data;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class RouteResponse {

    private String routeCode;

    private String routeName;

    private String sourceStation;

    private String destinationStation;

    private Integer totalDistance;

    private Integer totalStations;

    private List<String> stations;

    private Map<String, Integer> stationDistances;

    private String zoneCode;

    private Boolean isActive;

    private Boolean isCircular;

    private List<String> majorStations;

    private Map<String, String> stationAmenities;

    private String routeType;

    private String remarks;
} 