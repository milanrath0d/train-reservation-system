package com.milan.reservation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for train classes
 *
 * @author Milan Rathod
 */
@Entity
@Getter
@Setter
@ToString(exclude = {"coaches", "seatAvailabilities"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classes")
public class Class extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_code", nullable = false, unique = true)
    private String classCode;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "description")
    private String description;

    @Column(name = "is_ac")
    private boolean isAC;

    @Column(name = "has_wifi")
    private boolean hasWifi;

    @Column(name = "has_pantry")
    private boolean hasPantry;

    @Column(name = "has_charging_point")
    private boolean hasChargingPoint;

    @Column(name = "has_reading_light")
    private boolean hasReadingLight;

    @OneToMany(mappedBy = "trainClass")
    @Builder.Default
    private List<SeatAvailability> seatAvailabilities = new ArrayList<>();

    @OneToMany(mappedBy = "trainClass")
    @Builder.Default
    private List<Coach> coaches = new ArrayList<>();
}
