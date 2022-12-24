package com.milan.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Milan Rathod
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "class", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Class trainClass;

    @JoinColumn(name = "train", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    private Train train;

    private String coachNumber;

    private int seatCapacity;

    private int sequence;
}
