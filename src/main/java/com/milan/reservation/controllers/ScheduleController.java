package com.milan.reservation.controllers;

import com.milan.reservation.requests.UpdateScheduleRequest;
import com.milan.reservation.responses.ScheduleResponse;
import com.milan.reservation.services.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing train schedules
 *
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/train/{trainNumber}")
    public ResponseEntity<ScheduleResponse> getTrainSchedule(@PathVariable Long trainNumber) {
        return ResponseEntity.ok(scheduleService.getTrainSchedule(trainNumber));
    }

    @GetMapping("/train/{trainNumber}/date")
    public ResponseEntity<ScheduleResponse> getTrainScheduleByDate(
            @PathVariable Long trainNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(scheduleService.getTrainScheduleByDate(trainNumber, date));
    }

    @GetMapping("/station/{stationCode}")
    public ResponseEntity<List<ScheduleResponse>> getStationSchedule(
            @PathVariable String stationCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(scheduleService.getStationSchedule(stationCode, date));
    }

    @PostMapping("/train/{trainNumber}")
    public ResponseEntity<ScheduleResponse> updateTrainSchedule(
            @PathVariable Long trainNumber,
            @RequestBody @Valid UpdateScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.updateTrainSchedule(trainNumber, request));
    }

    @GetMapping("/train/{trainNumber}/delays")
    public ResponseEntity<List<ScheduleResponse>> getTrainDelayHistory(
            @PathVariable Long trainNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return ResponseEntity.ok(scheduleService.getTrainDelayHistory(trainNumber, fromDate, toDate));
    }

    @GetMapping("/train/{trainNumber}/running-days")
    public ResponseEntity<List<String>> getTrainRunningDays(@PathVariable Long trainNumber) {
        return ResponseEntity.ok(scheduleService.getTrainRunningDays(trainNumber));
    }

    @GetMapping("/train/{trainNumber}/next-available")
    public ResponseEntity<LocalDate> getNextAvailableDate(@PathVariable Long trainNumber) {
        return ResponseEntity.ok(scheduleService.getNextAvailableDate(trainNumber));
    }
} 