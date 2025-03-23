package com.milan.reservation.requests;

import com.milan.reservation.enums.Gender;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

/**
 * Request class for updating user profile
 *
 * @author Milan Rathod
 */
@Data
public class UpdateUserRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;

    private String address;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    private Gender gender;

    private String preferredLanguage;

    private String preferredCurrency;

    private String mealPreference;

    private String seatPreference;
} 