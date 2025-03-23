package com.milan.reservation.responses;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RefundResponse {

    private String refundId;

    private Double amount;

    private String status;

    private LocalDateTime createdAt;
} 