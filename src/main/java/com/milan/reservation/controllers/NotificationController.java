package com.milan.reservation.controllers;

import com.milan.reservation.requests.NotificationRequest;
import com.milan.reservation.responses.NotificationResponse;
import com.milan.reservation.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller for managing user notifications
 *
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(
            @PathVariable String userId,
            @RequestParam(required = false) Boolean unreadOnly) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId, unreadOnly));
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> sendNotification(@RequestBody @Valid NotificationRequest request) {
        return ResponseEntity.ok(notificationService.sendNotification(request));
    }

    @PostMapping("/{notificationId}/mark-read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable String notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{userId}/mark-all-read")
    public ResponseEntity<Void> markAllNotificationsAsRead(@PathVariable String userId) {
        notificationService.markAllNotificationsAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/preferences")
    public ResponseEntity<List<String>> getUserNotificationPreferences(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getUserNotificationPreferences(userId));
    }

    @PutMapping("/user/{userId}/preferences")
    public ResponseEntity<Void> updateUserNotificationPreferences(
            @PathVariable String userId,
            @RequestBody List<String> preferences) {
        notificationService.updateUserNotificationPreferences(userId, preferences);
        return ResponseEntity.ok().build();
    }
} 