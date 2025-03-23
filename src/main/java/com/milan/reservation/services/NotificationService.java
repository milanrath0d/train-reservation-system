package com.milan.reservation.services;

import com.milan.reservation.model.TrainBooking;
import com.milan.reservation.requests.NotificationRequest;
import com.milan.reservation.responses.NotificationResponse;

import java.util.List;

/**
 * Service interface for handling notifications in the train reservation system
 *
 * @author Milan Rathod
 */
public interface NotificationService {

    /**
     * Send a notification
     *
     * @param request The notification request
     * @return The notification response
     */
    NotificationResponse sendNotification(NotificationRequest request);

    /**
     * Get user notifications
     *
     * @param userId The user ID
     * @param unreadOnly Whether to get only unread notifications
     * @return List of notifications
     */
    List<NotificationResponse> getUserNotifications(String userId, Boolean unreadOnly);

    /**
     * Mark a notification as read
     *
     * @param notificationId The notification ID
     */
    void markNotificationAsRead(String notificationId);

    /**
     * Mark all notifications as read for a user
     *
     * @param userId The user ID
     */
    void markAllNotificationsAsRead(String userId);

    /**
     * Delete a notification
     *
     * @param notificationId The notification ID
     */
    void deleteNotification(String notificationId);

    /**
     * Get user notification preferences
     *
     * @param userId The user ID
     * @return List of notification preferences
     */
    List<String> getUserNotificationPreferences(String userId);

    /**
     * Update user notification preferences
     *
     * @param userId The user ID
     * @param preferences The notification preferences
     */
    void updateUserNotificationPreferences(String userId, List<String> preferences);

    /**
     * Send booking confirmation notification
     *
     * @param booking The booking details
     */
    void sendBookingConfirmation(TrainBooking booking);

    /**
     * Send cancellation confirmation notification
     *
     * @param booking The cancelled booking details
     */
    void sendCancellationConfirmation(TrainBooking booking);

    /**
     * Send booking reminder notification
     *
     * @param booking The booking details
     */
    void sendBookingReminder(TrainBooking booking);

    /**
     * Send waitlist confirmation notification
     *
     * @param booking The waitlisted booking details
     */
    void sendWaitlistConfirmation(TrainBooking booking);

    /**
     * Send waitlist to confirmed notification
     *
     * @param booking The confirmed booking details
     */
    void sendWaitlistToConfirmedNotification(TrainBooking booking);

    /**
     * Send PNR status notification
     *
     * @param booking The booking details
     */
    void sendPnrStatusNotification(TrainBooking booking);
} 