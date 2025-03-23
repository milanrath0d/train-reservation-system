package com.milan.reservation.controllers;

import com.milan.reservation.responses.ClassResponse;
import com.milan.reservation.services.ClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/classes")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public ResponseEntity<List<ClassResponse>> getAllClasses() {
        return ResponseEntity.ok(classService.getAllClasses());
    }

    @GetMapping("/{classCode}")
    public ResponseEntity<ClassResponse> getClassByCode(@PathVariable String classCode) {
        return ResponseEntity.ok(classService.getClassByCode(classCode));
    }

    @GetMapping("/train/{trainNumber}")
    public ResponseEntity<List<ClassResponse>> getClassesByTrain(@PathVariable Long trainNumber) {
        return ResponseEntity.ok(classService.getClassesByTrain(trainNumber));
    }

    @GetMapping("/train/{trainNumber}/availability")
    public ResponseEntity<List<ClassResponse>> getAvailableClasses(
            @PathVariable Long trainNumber,
            @RequestParam String sourceStation,
            @RequestParam String destinationStation) {
        return ResponseEntity.ok(classService.getAvailableClasses(trainNumber, sourceStation, destinationStation));
    }

    @GetMapping("/train/{trainNumber}/class/{classCode}/availability")
    public ResponseEntity<Boolean> isClassAvailable(
            @PathVariable Long trainNumber,
            @PathVariable String classCode) {
        return ResponseEntity.ok(classService.isClassAvailable(trainNumber, classCode));
    }

    @GetMapping("/train/{trainNumber}/class/{classCode}/seats")
    public ResponseEntity<Integer> getAvailableSeats(
            @PathVariable Long trainNumber,
            @PathVariable String classCode) {
        return ResponseEntity.ok(classService.getAvailableSeats(trainNumber, classCode));
    }

    @GetMapping("/train/{trainNumber}/class/{classCode}/waiting-list")
    public ResponseEntity<Integer> getWaitingListCount(
            @PathVariable Long trainNumber,
            @PathVariable String classCode) {
        return ResponseEntity.ok(classService.getWaitingListCount(trainNumber, classCode));
    }

    @GetMapping("/class/{classCode}/facilities")
    public ResponseEntity<List<String>> getClassFacilities(@PathVariable String classCode) {
        return ResponseEntity.ok(classService.getClassFacilities(classCode));
    }
}
