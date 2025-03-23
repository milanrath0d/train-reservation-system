package com.milan.reservation.model;

import com.milan.reservation.enums.TrainType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for trains
 *
 * @author Milan Rathod
 */
@Entity
@Getter
@Setter
@ToString(exclude = {"zone", "coaches", "routes", "schedules"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trains")
public class Train extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TrainType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "total_distance")
    private Integer totalDistance;

    @Column(name = "average_speed")
    private Integer averageSpeed;

    @Column(name = "has_pantry_car")
    private boolean hasPantryCar;

    @Column(name = "has_wifi")
    private boolean hasWifi;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Coach> coaches = new ArrayList<>();

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Route> routes = new ArrayList<>();

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TrainSchedule> schedules = new ArrayList<>();
}
