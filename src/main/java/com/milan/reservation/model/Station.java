package com.milan.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for railway stations
 *
 * @author Milan Rathod
 */
@Entity
@Getter
@Setter
@ToString(exclude = {"zone", "routes"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stations")
public class Station extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "station_code", nullable = false, unique = true)
    private String stationCode;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "number_of_platforms")
    private Integer numberOfPlatforms;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "elevation")
    private Double elevation;

    @Column(name = "has_retiring_room")
    private boolean hasRetiringRoom;

    @Column(name = "has_waiting_room")
    private boolean hasWaitingRoom;

    @Column(name = "has_food_plaza")
    private boolean hasFoodPlaza;

    @Column(name = "has_wifi")
    private boolean hasWifi;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;

    @OneToMany(mappedBy = "station")
    @Builder.Default
    private List<Route> routes = new ArrayList<>();
}
