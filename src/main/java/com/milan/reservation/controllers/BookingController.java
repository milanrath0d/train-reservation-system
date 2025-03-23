package com.milan.reservation.controllers;

import com.milan.reservation.enums.BookingStatus;
import com.milan.reservation.requests.BookingRequest;
import com.milan.reservation.responses.BookingResponse;
import com.milan.reservation.responses.PnrStatusResponse;
import com.milan.reservation.services.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody @Valid BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @GetMapping("/{pnrNumber}")
    public ResponseEntity<BookingResponse> getBookingByPnr(@PathVariable String pnrNumber) {
        return ResponseEntity.ok(bookingService.getBookingByPnr(pnrNumber));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getUserBookings(
            @PathVariable String userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return ResponseEntity.ok(bookingService.getUserBookings(userId, fromDate, toDate));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookingResponse>> getBookingsByStatus(@PathVariable BookingStatus status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    @GetMapping("/{pnrNumber}/status")
    public ResponseEntity<PnrStatusResponse> getPnrStatus(@PathVariable String pnrNumber) {
        return ResponseEntity.ok(bookingService.getPnrStatus(pnrNumber));
    }

    @PostMapping("/{pnrNumber}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable String pnrNumber,
            @RequestParam String reason) {
        return ResponseEntity.ok(bookingService.cancelBooking(pnrNumber, reason));
    }

    @GetMapping("/train/{trainNumber}")
    public ResponseEntity<List<BookingResponse>> getBookingsByTrain(
            @PathVariable Long trainNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate) {
        return ResponseEntity.ok(bookingService.getBookingsByTrain(trainNumber, journeyDate));
    }

    @GetMapping("/train/{trainNumber}/statistics")
    public ResponseEntity<Map<String, Integer>> getBookingStatistics(
            @PathVariable Long trainNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate) {
        return ResponseEntity.ok(bookingService.getBookingStatistics(trainNumber, journeyDate));
    }

    @GetMapping("/{pnrNumber}/cancellable")
    public ResponseEntity<Boolean> isBookingCancellable(@PathVariable String pnrNumber) {
        return ResponseEntity.ok(bookingService.isBookingCancellable(pnrNumber));
    }

    @GetMapping("/{pnrNumber}/refund-amount")
    public ResponseEntity<BigDecimal> calculateRefundAmount(@PathVariable String pnrNumber) {
        return ResponseEntity.ok(bookingService.calculateRefundAmount(pnrNumber));
    }
}
