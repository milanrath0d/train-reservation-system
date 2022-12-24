package com.milan.reservation.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

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

    @Type(type = "com.vladmihalcea.hibernate.type.array.ListArrayType",
        parameters = {@org.hibernate.annotations.Parameter(name = ListArrayType.SQL_ARRAY_TYPE, value = "text")})
    @Column(name = "frequencies", columnDefinition = "text[]")
    private Set<DayOfWeek> frequencies;
}
