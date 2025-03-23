package com.milan.reservation.services.impl;

import com.milan.reservation.model.TrainBooking;
import com.milan.reservation.requests.NotificationRequest;
import com.milan.reservation.responses.NotificationResponse;
import com.milan.reservation.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link NotificationService} for handling notifications
 *
 * @author Milan Rathod
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final JavaMailSender mailSender;

    @Value("${notification.email.from}")
    private String fromEmail;

    @Value("${notification.email.subject.booking}")
    private String bookingSubject;

    @Value("${notification.email.subject.cancellation}")
    private String cancellationSubject;

    @Override
    public NotificationResponse sendNotification(NotificationRequest request) {
        // TODO: Implement notification persistence
        String message = request.getMessage();
        sendEmail(request.getRecipientEmail(), request.getSubject(), message);
        return NotificationResponse.builder()
                .id("temp-id")
                .message(message)
                .status("SENT")
                .build();
    }

    @Override
    public List<NotificationResponse> getUserNotifications(String userId, Boolean unreadOnly) {
        // TODO: Implement fetching notifications from database
        return new ArrayList<>();
    }

    @Override
    public void markNotificationAsRead(String notificationId) {
        // TODO: Implement marking notification as read in database
    }

    @Override
    public void markAllNotificationsAsRead(String userId) {
        // TODO: Implement marking all notifications as read in database
    }

    @Override
    public void deleteNotification(String notificationId) {
        // TODO: Implement notification deletion from database
    }

    @Override
    public List<String> getUserNotificationPreferences(String userId) {
        // TODO: Implement fetching user preferences from database
        return new ArrayList<>();
    }

    @Override
    public void updateUserNotificationPreferences(String userId, List<String> preferences) {
        // TODO: Implement updating user preferences in database
    }

    @Override
    public void sendBookingConfirmation(TrainBooking booking) {
        String message = String.format(
            "Dear %s,\n\nYour train booking has been confirmed.\n\n" +
            "Booking Details:\n" +
            "PNR: %s\n" +
            "Train: %s (%s)\n" +
            "Journey Date: %s\n" +
            "From: %s\n" +
            "To: %s\n" +
            "Class: %s\n" +
            "Total Fare: ₹%s\n\n" +
            "Thank you for choosing our service.\n\n" +
            "Best regards,\nTrain Reservation Team",
            booking.getUser().getFullName(),
            booking.getPnrNumber(),
            booking.getTrain().getName(),
            booking.getTrain().getNumber(),
            booking.getJourneyDate(),
            booking.getSourceStation(),
            booking.getDestinationStation(),
            booking.getTrainClass().getClassName(),
            booking.getTotalFare().toString()
        );

        sendEmail(booking.getUser().getEmail(), bookingSubject, message);
    }

    @Override
    public void sendCancellationConfirmation(TrainBooking booking) {
        String message = String.format(
            "Dear %s,\n\nYour train booking has been cancelled.\n\n" +
            "Booking Details:\n" +
            "PNR: %s\n" +
            "Train: %s (%s)\n" +
            "Journey Date: %s\n" +
            "Refund Amount: ₹%s\n\n" +
            "The refund will be processed within 5-7 working days.\n\n" +
            "Thank you for using our service.\n\n" +
            "Best regards,\nTrain Reservation Team",
            booking.getUser().getFullName(),
            booking.getPnrNumber(),
            booking.getTrain().getName(),
            booking.getTrain().getNumber(),
            booking.getJourneyDate(),
            booking.getRefundAmount().toString()
        );

        sendEmail(booking.getUser().getEmail(), cancellationSubject, message);
    }

    @Override
    public void sendBookingReminder(TrainBooking booking) {
        String message = String.format(
            "Dear %s,\n\nThis is a reminder for your upcoming train journey.\n\n" +
            "Booking Details:\n" +
            "PNR: %s\n" +
            "Train: %s (%s)\n" +
            "Journey Date: %s\n" +
            "From: %s at %s\n" +
            "To: %s\n" +
            "Class: %s\n\n" +
            "Have a pleasant journey!\n\n" +
            "Best regards,\nTrain Reservation Team",
            booking.getUser().getFullName(),
            booking.getPnrNumber(),
            booking.getTrain().getName(),
            booking.getTrain().getNumber(),
            booking.getJourneyDate(),
            booking.getSourceStation(),
            booking.getDepartureTime(),
            booking.getDestinationStation(),
            booking.getTrainClass().getClassName()
        );

        sendEmail(booking.getUser().getEmail(), "Journey Reminder: " + booking.getPnrNumber(), message);
    }

    @Override
    public void sendWaitlistConfirmation(TrainBooking booking) {
        String message = String.format(
            "Dear %s,\n\nYour train booking has been waitlisted.\n\n" +
            "Booking Details:\n" +
            "PNR: %s\n" +
            "Train: %s (%s)\n" +
            "Journey Date: %s\n" +
            "From: %s\n" +
            "To: %s\n" +
            "Class: %s\n" +
            "Waitlist Position: %d\n\n" +
            "We will notify you if your booking gets confirmed.\n\n" +
            "Best regards,\nTrain Reservation Team",
            booking.getUser().getFullName(),
            booking.getPnrNumber(),
            booking.getTrain().getName(),
            booking.getTrain().getNumber(),
            booking.getJourneyDate(),
            booking.getSourceStation(),
            booking.getDestinationStation(),
            booking.getTrainClass().getClassName(),
            booking.getWaitlistNumber()
        );

        sendEmail(booking.getUser().getEmail(), "Waitlist Confirmation: " + booking.getPnrNumber(), message);
    }

    @Override
    public void sendWaitlistToConfirmedNotification(TrainBooking booking) {
        String message = String.format(
            "Dear %s,\n\nGreat news! Your waitlisted booking has been confirmed.\n\n" +
            "Booking Details:\n" +
            "PNR: %s\n" +
            "Train: %s (%s)\n" +
            "Journey Date: %s\n" +
            "From: %s\n" +
            "To: %s\n" +
            "Class: %s\n\n" +
            "Have a pleasant journey!\n\n" +
            "Best regards,\nTrain Reservation Team",
            booking.getUser().getFullName(),
            booking.getPnrNumber(),
            booking.getTrain().getName(),
            booking.getTrain().getNumber(),
            booking.getJourneyDate(),
            booking.getSourceStation(),
            booking.getDestinationStation(),
            booking.getTrainClass().getClassName()
        );

        sendEmail(booking.getUser().getEmail(), "Booking Confirmed: " + booking.getPnrNumber(), message);
    }

    @Override
    public void sendPnrStatusNotification(TrainBooking booking) {
        String message = String.format(
            "Dear %s,\n\nHere is your PNR status:\n\n" +
            "PNR: %s\n" +
            "Status: %s\n" +
            "Train: %s (%s)\n" +
            "Journey Date: %s\n" +
            "From: %s\n" +
            "To: %s\n" +
            "Class: %s\n\n" +
            "Best regards,\nTrain Reservation Team",
            booking.getUser().getFullName(),
            booking.getPnrNumber(),
            booking.getStatus(),
            booking.getTrain().getName(),
            booking.getTrain().getNumber(),
            booking.getJourneyDate(),
            booking.getSourceStation(),
            booking.getDestinationStation(),
            booking.getTrainClass().getClassName()
        );

        sendEmail(booking.getUser().getEmail(), "PNR Status: " + booking.getPnrNumber(), message);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
} 