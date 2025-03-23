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
public class BookingResponse {
    private String pnrNumber;
    private Long trainNumber;
    private String trainName;
    private String trainClass;
    private String sourceStation;
    private String destinationStation;
    private LocalDate journeyDate;
    private LocalDateTime bookingDateTime;
    private BookingStatus bookingStatus;
    private String paymentStatus;
    private BigDecimal totalFare;
    private BigDecimal finalAmount;
    private List<PassengerResponse> passengers;
    private String transactionId;
    private LocalDateTime cancellationDateTime;
    private String cancellationReason;
    private BigDecimal refundAmount;
    private String refundStatus;
    private Integer waitingListNumber;
    private String coachNumber;
    private LocalDateTime boardingTime;
    private String platformNumber;
    private String bookingSource;
    private Map<String, String> additionalInfo;
} 