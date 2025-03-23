package com.milan.reservation.requests;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class RefundRequest {

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @NotNull(message = "Refund amount is required")
    @Positive(message = "Refund amount must be positive")
    private Double refundAmount;

    @NotBlank(message = "Refund reason is required")
    private String refundReason;

    private String cancellationId;

    private String remarks;

    private Boolean isPartialRefund;

    private String refundTo;

    private String userEmail;

    private String userPhone;
} 