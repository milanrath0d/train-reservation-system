package com.milan.reservation.requests;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class UpdateFareRequest {

    @NotNull(message = "Train number is required")
    @Positive(message = "Train number must be positive")
    private Long trainNumber;

    @NotBlank(message = "Class code is required")
    private String classCode;

    @NotNull(message = "Base fare is required")
    @Positive(message = "Base fare must be positive")
    private BigDecimal baseFare;

    private BigDecimal dynamicFareMultiplier;

    private BigDecimal serviceTaxPercentage;

    private BigDecimal convenienceFee;

    private Boolean isDiscountApplicable;

    private Integer maxDiscountPercentage;

    private String fareRuleCode;

    @NotNull(message = "Effective from date is required")
    private String effectiveFrom;
} 