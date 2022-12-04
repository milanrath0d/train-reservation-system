package com.milan.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;

/**
 * @author Milan Rathod
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "route")
public class Route {

    @Id
    private Long id;

    private Long trainNumber;

    private String stationCode;

    private String stationName;

    private int distanceFromOrigin;

    private LocalTime arrivalTime;

    private int halt;

    private int sequence;
}
