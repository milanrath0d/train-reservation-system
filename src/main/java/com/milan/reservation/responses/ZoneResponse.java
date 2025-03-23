package com.milan.reservation.responses;

import lombok.Data;
import lombok.Builder;

import java.util.List;
import java.util.Map;

/**
 * Response class for railway zone details
 *
 * @author Milan Rathod
 */
@Data
@Builder
public class ZoneResponse {

    private String zoneCode;

    private String zoneName;

    private String region;

    private String headquarters;

    private Integer totalStations;

    private Integer totalTrains;

    private List<String> stations;

    private boolean isActive;

    private String description;

    private List<String> routes;

    private List<Long> trains;

    private Integer totalRoutes;

    private Map<String, Integer> statistics;

    private String remarks;
} 