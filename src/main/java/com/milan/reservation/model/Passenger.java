package com.milan.reservation.model;

import com.milan.reservation.enums.Gender;
import com.milan.reservation.enums.SeatStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Milan Rathod
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_booking_id")
    private TrainBooking trainBooking;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    private String seatNumber;

    private String coachNumber;

    @Builder.Default
    private boolean isLeadPassenger = false;

    private String nationality;
    
    @Column(name = "id_type")
    private String idType;
    
    @Column(name = "id_number")
    private String idNumber;
    
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;
    
    @Column(name = "meal_preference")
    private String mealPreference;
    
    @Column(name = "special_assistance")
    private String specialAssistance;
} 