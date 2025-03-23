package com.milan.reservation.services;

import com.milan.reservation.responses.StationResponse;
import com.milan.reservation.responses.TrainResponse;
import com.milan.reservation.requests.AddStationRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Service interface for managing railway stations
 *
 * @author Milan Rathod
 */
public interface StationService {

    /**
     * Search stations based on query or zone code
     */
    List<StationResponse> searchStations(String query, String zoneCode);

    /**
     * Get station details by station code
     */
    StationResponse getStation(String stationCode);

    /**
     * Get list of trains available at a station on a given date
     */
    List<TrainResponse> getTrainsAtStation(String stationCode, LocalDate date);

    /**
     * Add a new station
     */
    StationResponse addStation(AddStationRequest request);

    /**
     * Update an existing station
     */
    StationResponse updateStation(String stationCode, AddStationRequest request);

    /**
     * Get list of all stations
     */
    List<StationResponse> getAllStations();

    /**
     * Get station facilities
     */
    List<String> getStationFacilities(String stationCode);

    /**
     * Get station statistics (platforms, daily trains, etc.)
     */
    Map<String, Integer> getStationStatistics(String stationCode);

    /**
     * Get list of connecting stations
     */
    List<StationResponse> getConnectingStations(String stationCode);

    /**
     * Check if station is active
     */
    boolean isStationActive(String stationCode);
}
