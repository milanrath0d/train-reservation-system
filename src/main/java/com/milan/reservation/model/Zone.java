package com.milan.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for railway zones
 *
 * @author Milan Rathod
 */
@Entity
@Getter
@Setter
@ToString(exclude = {"stations", "trains"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "zones")
public class Zone extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zone_code", nullable = false, unique = true)
    private String zoneCode;

    @Column(name = "zone_name", nullable = false)
    private String zoneName;

    @Column(name = "region")
    private String region;

    @Column(name = "headquarters")
    private String headquarters;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "zone")
    @Builder.Default
    private List<Train> trains = new ArrayList<>();

    @OneToMany(mappedBy = "zone")
    @Builder.Default
    private List<Station> stations = new ArrayList<>();
}
