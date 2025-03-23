package com.milan.reservation.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response class for notifications
 *
 * @author Milan Rathod
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private String id;
    private String message;
    private String status;
    private String type;
    private String priority;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
    private boolean isRead;
} 