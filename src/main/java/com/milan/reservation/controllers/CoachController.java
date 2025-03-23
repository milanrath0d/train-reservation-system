package com.milan.reservation.controllers;

import com.milan.reservation.responses.CoachResponse;
import com.milan.reservation.services.CoachService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/coaches")
public class CoachController {

    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping("/train/{trainNumber}")
    public ResponseEntity<List<CoachResponse>> getAllCoaches(@PathVariable Long trainNumber) {
        return ResponseEntity.ok(coachService.getAllCoaches(trainNumber));
    }

    @GetMapping("/train/{trainNumber}/coach/{coachNumber}")
    public ResponseEntity<CoachResponse> getCoachByNumber(
            @PathVariable Long trainNumber,
            @PathVariable String coachNumber) {
        return ResponseEntity.ok(coachService.getCoachByNumber(trainNumber, coachNumber));
    }

    @GetMapping("/train/{trainNumber}/class/{classCode}")
    public ResponseEntity<List<CoachResponse>> getCoachesByClass(
            @PathVariable Long trainNumber,
            @PathVariable String classCode) {
        return ResponseEntity.ok(coachService.getCoachesByClass(trainNumber, classCode));
    }

    @GetMapping("/train/{trainNumber}/coach/{coachNumber}/seats")
    public ResponseEntity<Integer> getAvailableSeats(
            @PathVariable Long trainNumber,
            @PathVariable String coachNumber) {
        return ResponseEntity.ok(coachService.getAvailableSeats(trainNumber, coachNumber));
    }

    @GetMapping("/train/{trainNumber}/coach/{coachNumber}/berths")
    public ResponseEntity<List<String>> getAvailableBerths(
            @PathVariable Long trainNumber,
            @PathVariable String coachNumber) {
        return ResponseEntity.ok(coachService.getAvailableBerths(trainNumber, coachNumber));
    }

    @GetMapping("/train/{trainNumber}/coach/{coachNumber}/availability")
    public ResponseEntity<Boolean> isCoachAvailable(
            @PathVariable Long trainNumber,
            @PathVariable String coachNumber) {
        return ResponseEntity.ok(coachService.isCoachAvailable(trainNumber, coachNumber));
    }

    @GetMapping("/train/{trainNumber}/coach/{coachNumber}/facilities")
    public ResponseEntity<List<String>> getCoachFacilities(
            @PathVariable Long trainNumber,
            @PathVariable String coachNumber) {
        return ResponseEntity.ok(coachService.getCoachFacilities(trainNumber, coachNumber));
    }

    @GetMapping("/train/{trainNumber}/occupancy")
    public ResponseEntity<Map<String, Integer>> getCoachOccupancy(@PathVariable Long trainNumber) {
        return ResponseEntity.ok(coachService.getCoachOccupancy(trainNumber));
    }
}
