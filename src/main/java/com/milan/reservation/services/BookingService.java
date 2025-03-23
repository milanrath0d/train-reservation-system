package com.milan.reservation.services;

import com.milan.reservation.enums.BookingStatus;
import com.milan.reservation.requests.BookingRequest;
import com.milan.reservation.responses.BookingResponse;
import com.milan.reservation.responses.PnrStatusResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

    BookingResponse getBookingByPnr(String pnrNumber);

    List<BookingResponse> getUserBookings(String userId, LocalDate fromDate, LocalDate toDate);

    List<BookingResponse> getBookingsByStatus(BookingStatus status);

    PnrStatusResponse getPnrStatus(String pnrNumber);

    BookingResponse cancelBooking(String pnrNumber, String reason);

    List<BookingResponse> getBookingsByTrain(Long trainNumber, LocalDate journeyDate);

    Map<String, Integer> getBookingStatistics(Long trainNumber, LocalDate journeyDate);

    Boolean isBookingCancellable(String pnrNumber);

    BigDecimal calculateRefundAmount(String pnrNumber);
} 