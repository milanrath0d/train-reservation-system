package com.milan.reservation.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Milan Rathod
 */
@Data
@Builder
public class TrainResponse {

    private String trainName;

    private Long trainNumber;

    private String sourceStation;

    private String destinationStation;

    private List<String> frequencies;

    private String startTime;

    private String endTime;
}
