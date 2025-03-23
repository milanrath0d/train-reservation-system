package com.milan.reservation.controllers;

import com.milan.reservation.requests.AddStationRequest;
import com.milan.reservation.responses.StationResponse;
import com.milan.reservation.responses.TrainResponse;
import com.milan.reservation.services.StationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing railway stations
 *
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/stations")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<StationResponse>> searchStations(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String zoneCode) {
        return ResponseEntity.ok(stationService.searchStations(query, zoneCode));
    }

    @GetMapping("/{stationCode}")
    public ResponseEntity<StationResponse> getStation(@PathVariable String stationCode) {
        return ResponseEntity.ok(stationService.getStation(stationCode));
    }

    @GetMapping("/{stationCode}/trains")
    public ResponseEntity<List<TrainResponse>> getTrainsAtStation(
            @PathVariable String stationCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(stationService.getTrainsAtStation(stationCode, date));
    }

    @PostMapping
    public ResponseEntity<StationResponse> addStation(@RequestBody @Valid AddStationRequest request) {
        return ResponseEntity.ok(stationService.addStation(request));
    }

    @PutMapping("/{stationCode}")
    public ResponseEntity<StationResponse> updateStation(
            @PathVariable String stationCode,
            @RequestBody @Valid AddStationRequest request) {
        return ResponseEntity.ok(stationService.updateStation(stationCode, request));
    }

    @GetMapping
    public ResponseEntity<List<StationResponse>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @GetMapping("/{stationCode}/facilities")
    public ResponseEntity<List<String>> getStationFacilities(@PathVariable String stationCode) {
        return ResponseEntity.ok(stationService.getStationFacilities(stationCode));
    }

    @GetMapping("/{stationCode}/statistics")
    public ResponseEntity<Map<String, Integer>> getStationStatistics(@PathVariable String stationCode) {
        return ResponseEntity.ok(stationService.getStationStatistics(stationCode));
    }

    @GetMapping("/{stationCode}/connecting")
    public ResponseEntity<List<StationResponse>> getConnectingStations(@PathVariable String stationCode) {
        return ResponseEntity.ok(stationService.getConnectingStations(stationCode));
    }

    @GetMapping("/{stationCode}/active")
    public ResponseEntity<Boolean> isStationActive(@PathVariable String stationCode) {
        return ResponseEntity.ok(stationService.isStationActive(stationCode));
    }
}
