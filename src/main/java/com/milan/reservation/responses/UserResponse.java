package com.milan.reservation.responses;

import com.milan.reservation.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Response class for user details
 *
 * @author Milan Rathod
 */
@Data
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String address;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String preferredLanguage;

    private String preferredCurrency;

    private boolean emailNotificationsEnabled;

    private boolean smsNotificationsEnabled;

    private String mealPreference;

    private String seatPreference;

    private boolean isActive;

    private LocalDate registrationDate;

    private LocalDateTime lastLoginDate;

    private String firstName;

    private String lastName;

    private String city;

    private String state;

    private String pinCode;

    private String bio;

    private String dateFormat;

    private String timeZone;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer totalBookings;

    private Integer activeBookings;

    private Map<String, Object> preferences;

    private Map<String, Integer> bookingStats;
} 