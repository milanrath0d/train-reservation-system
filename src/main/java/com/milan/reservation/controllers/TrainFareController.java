package com.milan.reservation.controllers;

import com.milan.reservation.requests.UpdateFareRequest;
import com.milan.reservation.responses.FareResponse;
import com.milan.reservation.services.TrainFareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/fares")
public class TrainFareController {

    private final TrainFareService trainFareService;

    public TrainFareController(TrainFareService trainFareService) {
        this.trainFareService = trainFareService;
    }

    @GetMapping("/train/{trainNumber}")
    public ResponseEntity<List<FareResponse>> getTrainFares(
            @PathVariable Long trainNumber,
            @RequestParam(required = false) String classCode) {
        return ResponseEntity.ok(trainFareService.getTrainFares(trainNumber, classCode));
    }

    @GetMapping("/route")
    public ResponseEntity<List<FareResponse>> getRouteFares(
            @RequestParam String sourceStation,
            @RequestParam String destinationStation,
            @RequestParam(required = false) String classCode) {
        return ResponseEntity.ok(trainFareService.getRouteFares(sourceStation, destinationStation, classCode));
    }

    @PostMapping("/train/{trainNumber}")
    public ResponseEntity<List<FareResponse>> updateTrainFares(
            @PathVariable Long trainNumber,
            @RequestBody @Valid UpdateFareRequest request) {
        return ResponseEntity.ok(trainFareService.updateTrainFares(trainNumber, request));
    }

    @PostMapping("/bulk-update")
    public ResponseEntity<Void> bulkUpdateFares(@RequestBody @Valid List<UpdateFareRequest> requests) {
        trainFareService.bulkUpdateFares(requests);
        return ResponseEntity.ok().build();
    }
}
