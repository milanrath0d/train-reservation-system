package com.milan.reservation.responses;

import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class FareResponse {

    private Long trainNumber;

    private String classCode;

    private String className;

    private String fromStation;

    private String toStation;

    private Integer distanceInKm;

    private BigDecimal baseFare;

    private BigDecimal dynamicFare;

    private BigDecimal dynamicFareMultiplier;

    private Boolean isDynamicPricingEnabled;

    private BigDecimal serviceTaxPercentage;

    private BigDecimal convenienceFee;

    private BigDecimal discountedFare;

    private String fareRuleCode;

    private String routeCode;

    private LocalDateTime effectiveFrom;

    private LocalDateTime effectiveTo;
} 