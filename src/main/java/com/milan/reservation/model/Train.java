package com.milan.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

/**
 * @author Milan Rathod
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "train")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long trainNumber;

    private String trainName;

    @Enumerated(EnumType.STRING)
    private TrainType trainType;

    @JoinColumn(name = "zone", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Zone zone;

    private LocalTime startTime;

    private LocalTime endTime;

    private String source;

    private String destination;

    private String frequency;
}
