package com.milan.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for train coaches
 *
 * @author Milan Rathod
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coaches")
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"seats"})
public class Coach extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coach_number", nullable = false)
    private String coachNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private Class trainClass;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Seat> seats = new ArrayList<>();

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    @Column(name = "has_pantry")
    private boolean hasPantry;

    @Column(name = "has_wifi")
    private boolean hasWifi;

    @Column(name = "has_charging_point")
    private boolean hasChargingPoint;

    @Column(name = "has_reading_light")
    private boolean hasReadingLight;

    @Column(name = "seat_capacity")
    private int seatCapacity;

    @Column(name = "total_rows")
    private int totalRows;

    @Column(name = "seats_per_row")
    private int seatsPerRow;

    @Column(name = "has_side_berths")
    private boolean hasSideBerths;

    @Column(name = "side_berth_count")
    private Integer sideBerthCount;

    @Column(name = "berth_configuration", columnDefinition = "TEXT")
    private String berthConfiguration;  // JSON string containing berth layout

    @Column(name = "is_ac")
    private boolean isAc;

    @Column(name = "maintenance_due_date")
    private LocalDate maintenanceDueDate;
}
