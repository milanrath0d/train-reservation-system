package com.milan.reservation.responses;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response class for train schedule details
 *
 * @author Milan Rathod
 */
@Data
public class ScheduleResponse {
    private Long trainNumber;
    private String trainName;
    private List<StationSchedule> stations;
    private List<String> runningDays;
    private boolean active;
    private LocalDateTime lastUpdated;

    @Data
    public static class StationSchedule {
        private String stationCode;
        private String stationName;
        private LocalDateTime arrivalTime;
        private LocalDateTime departureTime;
        private int platformNumber;
        private int distanceFromSource;
        private int haltTime;
    }
} 