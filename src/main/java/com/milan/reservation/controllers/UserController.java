package com.milan.reservation.controllers;

import com.milan.reservation.enums.BookingStatus;
import com.milan.reservation.requests.UpdateUserRequest;
import com.milan.reservation.responses.UserResponse;
import com.milan.reservation.responses.BookingResponse;
import com.milan.reservation.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(@RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateProfile(request));
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> getUserBookings(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) BookingStatus status) {
        return ResponseEntity.ok(userService.getUserBookings(fromDate, toDate, status));
    }

    @GetMapping("/preferences")
    public ResponseEntity<Map<String, Object>> getUserPreferences() {
        return ResponseEntity.ok(userService.getUserPreferences());
    }

    @PutMapping("/preferences")
    public ResponseEntity<Void> updatePreferences(@RequestBody Map<String, Object> preferences) {
        userService.updatePreferences(preferences);
        return ResponseEntity.ok().build();
    }
} 