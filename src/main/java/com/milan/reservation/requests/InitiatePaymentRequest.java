package com.milan.reservation.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class InitiatePaymentRequest {

    @NotBlank(message = "Booking reference is required")
    private String bookingReference;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    private String currency;

    private String description;

    private String returnUrl;

    private String cancelUrl;

    private String paymentGateway;

    private String userEmail;

    private String userPhone;
} 