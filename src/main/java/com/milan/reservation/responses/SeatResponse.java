package com.milan.reservation.responses;

import com.milan.reservation.enums.SeatStatus;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class SeatResponse {
    private String seatNumber;
    private SeatStatus status;
    private boolean isWindow;
    private boolean isAisle;
    private int rowNumber;
} 