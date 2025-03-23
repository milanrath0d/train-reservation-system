package com.milan.reservation.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CancellationRequest {
    @NotBlank
    private String reason;

    private List<Long> passengerIds;  // Optional: for partial cancellation
} 