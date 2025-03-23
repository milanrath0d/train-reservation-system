package com.milan.reservation.requests;

import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Request class for updating train schedule
 *
 * @author Milan Rathod
 */
@Data
public class UpdateScheduleRequest {
    @NotEmpty(message = "Running days cannot be empty")
    private List<String> runningDays;

    @NotEmpty(message = "Station schedules cannot be empty")
    @Valid
    private List<StationScheduleUpdate> stations;

    @Data
    public static class StationScheduleUpdate {
        @NotNull(message = "Station code is required")
        private String stationCode;

        @NotNull(message = "Arrival time is required")
        private LocalDateTime arrivalTime;

        @NotNull(message = "Departure time is required")
        private LocalDateTime departureTime;

        @Min(value = 1, message = "Platform number must be at least 1")
        private int platformNumber;

        @Min(value = 0, message = "Distance from source cannot be negative")
        private int distanceFromSource;

        @Min(value = 0, message = "Halt time cannot be negative")
        private int haltTime;

        private boolean isCommercialStop;
    }
} 