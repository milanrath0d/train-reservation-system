package com.milan.reservation.request;

import com.milan.reservation.enums.TrainType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

/**
 * @author Milan Rathod
 */
@Data
public class AddTrainRequest {

    private Long trainNumber;

    private String trainName;

    private TrainType trainType;

    private String zoneCode;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    private String source;

    private String destination;

    private List<String> frequencies;
}
