package com.milan.reservation.services.impl;

import com.milan.reservation.enums.BookingStatus;
import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.User;
import com.milan.reservation.repository.BookingRepository;
import com.milan.reservation.repository.UserRepository;
import com.milan.reservation.requests.UpdateUserRequest;
import com.milan.reservation.responses.BookingResponse;
import com.milan.reservation.responses.UserResponse;
import com.milan.reservation.services.BookingService;
import com.milan.reservation.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    @Override
    public UserResponse getCurrentUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateProfile(UpdateUserRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setPreferredLanguage(request.getPreferredLanguage());
        user.setPreferredCurrency(request.getPreferredCurrency());

        user = userRepository.save(user);
        return mapToUserResponse(user);
    }

    @Override
    public List<BookingResponse> getUserBookings(LocalDate fromDate, LocalDate toDate, BookingStatus status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return bookingService.getUserBookings(user.getId().toString(), fromDate, toDate);
    }

    @Override
    public Map<String, Object> getUserPreferences() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Map<String, Object> preferences = new HashMap<>();
        preferences.put("language", user.getPreferredLanguage());
        preferences.put("currency", user.getPreferredCurrency());
        preferences.put("emailNotifications", user.isEmailNotificationsEnabled());
        preferences.put("smsNotifications", user.isSmsNotificationsEnabled());
        preferences.put("mealPreference", user.getMealPreference());
        preferences.put("seatPreference", user.getSeatPreference());
        return preferences;
    }

    @Override
    @Transactional
    public void updatePreferences(Map<String, Object> preferences) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (preferences.containsKey("language")) {
            user.setPreferredLanguage((String) preferences.get("language"));
        }
        if (preferences.containsKey("currency")) {
            user.setPreferredCurrency((String) preferences.get("currency"));
        }
        if (preferences.containsKey("mealPreference")) {
            user.setMealPreference((String) preferences.get("mealPreference"));
        }
        if (preferences.containsKey("seatPreference")) {
            user.setSeatPreference((String) preferences.get("seatPreference"));
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateNotificationSettings(Boolean emailEnabled, Boolean smsEnabled) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (emailEnabled != null) {
            user.setEmailNotificationsEnabled(emailEnabled);
        }
        if (smsEnabled != null) {
            user.setSmsNotificationsEnabled(smsEnabled);
        }

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateAccount(String reason) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setActive(false);
        user.setDeactivationReason(reason);
        user.setDeactivationDate(LocalDate.now());

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void reactivateAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setActive(true);
        user.setDeactivationReason(null);
        user.setDeactivationDate(null);

        userRepository.save(user);
    }

    @Override
    public Map<String, Integer> getBookingStatistics(LocalDate fromDate, LocalDate toDate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("totalBookings", bookingRepository.countByUserAndDateRange(user, fromDate, toDate));
        statistics.put("confirmedBookings", bookingRepository.countByUserAndStatusAndDateRange(
                user, BookingStatus.CONFIRMED, fromDate, toDate));
        statistics.put("cancelledBookings", bookingRepository.countByUserAndStatusAndDateRange(
                user, BookingStatus.CANCELLED, fromDate, toDate));
        statistics.put("waitlistedBookings", bookingRepository.countByUserAndStatusAndDateRange(
                user, BookingStatus.WAITLISTED, fromDate, toDate));
        return statistics;
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .preferredLanguage(user.getPreferredLanguage())
                .preferredCurrency(user.getPreferredCurrency())
                .emailNotificationsEnabled(user.isEmailNotificationsEnabled())
                .smsNotificationsEnabled(user.isSmsNotificationsEnabled())
                .mealPreference(user.getMealPreference())
                .seatPreference(user.getSeatPreference())
                .isActive(user.isActive())
                .registrationDate(user.getRegistrationDate())
                .lastLoginDate(user.getLastLoginDate())
                .build();
    }
} 