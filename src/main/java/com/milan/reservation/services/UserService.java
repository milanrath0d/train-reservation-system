package com.milan.reservation.services;

import com.milan.reservation.enums.BookingStatus;
import com.milan.reservation.requests.UpdateUserRequest;
import com.milan.reservation.responses.BookingResponse;
import com.milan.reservation.responses.UserResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserService {

    UserResponse getCurrentUserProfile();

    UserResponse updateProfile(UpdateUserRequest request);

    List<BookingResponse> getUserBookings(LocalDate fromDate, LocalDate toDate, BookingStatus status);

    Map<String, Object> getUserPreferences();

    void updatePreferences(Map<String, Object> preferences);

    void updateNotificationSettings(Boolean emailEnabled, Boolean smsEnabled);

    void deactivateAccount(String reason);

    void reactivateAccount();

    Map<String, Integer> getBookingStatistics(LocalDate fromDate, LocalDate toDate);
} 