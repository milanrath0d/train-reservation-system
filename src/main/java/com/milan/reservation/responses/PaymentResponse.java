package com.milan.reservation.responses;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private String transactionId;

    private String bookingReference;

    private Double amount;

    private String currency;

    private String paymentMethod;

    private String paymentStatus;

    private LocalDateTime paymentDateTime;

    private String paymentGateway;

    private String gatewayTransactionId;

    private String gatewayResponse;

    private String paymentUrl;

    private String failureReason;

    private LocalDateTime expiryDateTime;

    private Boolean isRefundable;

    private BigDecimal refundableAmount;

    private String status;

    private LocalDateTime createdAt;
} 