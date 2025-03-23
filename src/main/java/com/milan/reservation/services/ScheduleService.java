package com.milan.reservation.services;

import com.milan.reservation.requests.UpdateScheduleRequest;
import com.milan.reservation.responses.ScheduleResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing train schedules
 *
 * @author Milan Rathod
 */
public interface ScheduleService {

    /**
     * Get current schedule for a train
     */
    ScheduleResponse getTrainSchedule(Long trainNumber);

    /**
     * Get schedule for a train on a specific date
     */
    ScheduleResponse getTrainScheduleByDate(Long trainNumber, LocalDate date);

    /**
     * Get schedules for all trains at a station on a specific date
     */
    List<ScheduleResponse> getStationSchedule(String stationCode, LocalDate date);

    /**
     * Update schedule for a train
     */
    ScheduleResponse updateTrainSchedule(Long trainNumber, UpdateScheduleRequest request);

    /**
     * Get delay history for a train between dates
     */
    List<ScheduleResponse> getTrainDelayHistory(Long trainNumber, LocalDate fromDate, LocalDate toDate);

    /**
     * Get running days for a train
     */
    List<String> getTrainRunningDays(Long trainNumber);

    /**
     * Get next available date for a train
     */
    LocalDate getNextAvailableDate(Long trainNumber);
} 