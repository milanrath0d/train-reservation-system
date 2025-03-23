package com.milan.reservation.controllers;

import com.milan.reservation.responses.TrainResponse;
import com.milan.reservation.services.TrainService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/trains")
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<TrainResponse>> searchTrains(
            @RequestParam String sourceStation,
            @RequestParam String destinationStation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate) {
        return ResponseEntity.ok(trainService.searchTrains(sourceStation, destinationStation, journeyDate));
    }

    @GetMapping("/{trainNumber}")
    public ResponseEntity<TrainResponse> getTrainByNumber(@PathVariable("trainNumber") Long trainNumber) {
        return ResponseEntity.ok(trainService.getTrainByNumber(trainNumber));
    }

    @GetMapping("/route/{routeCode}")
    public ResponseEntity<List<TrainResponse>> getTrainsByRoute(@PathVariable String routeCode) {
        return ResponseEntity.ok(trainService.getTrainsByRoute(routeCode));
    }

    @GetMapping("/station/{stationCode}")
    public ResponseEntity<List<TrainResponse>> getTrainsByStation(
            @PathVariable String stationCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(trainService.getTrainsByStation(stationCode, date));
    }

    @GetMapping("/{trainNumber}/schedule")
    public ResponseEntity<List<String>> getTrainSchedule(@PathVariable Long trainNumber) {
        return ResponseEntity.ok(trainService.getTrainSchedule(trainNumber));
    }

    @GetMapping("/{trainNumber}/running-status")
    public ResponseEntity<Boolean> isTrainRunningOnDate(
            @PathVariable Long trainNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(trainService.isTrainRunningOnDate(trainNumber, date));
    }

    @GetMapping("/{trainNumber}/stations")
    public ResponseEntity<List<String>> getIntermediateStations(@PathVariable Long trainNumber) {
        return ResponseEntity.ok(trainService.getIntermediateStations(trainNumber));
    }

    @GetMapping("/{trainNumber}/duration")
    public ResponseEntity<String> getJourneyDuration(
            @PathVariable Long trainNumber,
            @RequestParam String sourceStation,
            @RequestParam String destinationStation) {
        return ResponseEntity.ok(trainService.getJourneyDuration(trainNumber, sourceStation, destinationStation));
    }

    @GetMapping("/connecting")
    public ResponseEntity<List<TrainResponse>> getConnectingTrains(
            @RequestParam String sourceStation,
            @RequestParam String destinationStation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(trainService.getConnectingTrains(sourceStation, destinationStation, date));
    }
}
