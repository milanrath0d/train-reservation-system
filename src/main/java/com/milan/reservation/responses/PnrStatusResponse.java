package com.milan.reservation.responses;

import com.milan.reservation.enums.BookingStatus;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class PnrStatusResponse {

    private String pnrNumber;

    private Long trainNumber;

    private String trainName;

    private String sourceStation;

    private String destinationStation;

    private LocalDate journeyDate;

    private LocalDateTime bookingDateTime;

    private BookingStatus bookingStatus;

    private String paymentStatus;

    private List<PassengerResponse> passengers;

    private String cancellationReason;

    private LocalDateTime cancellationDateTime;

    private BigDecimal refundAmount;

    private String refundStatus;

    private LocalDateTime boardingTime;

    private String platformNumber;

    private String coachPosition;

    private Map<String, String> additionalInfo;
} 