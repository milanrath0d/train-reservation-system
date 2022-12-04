package com.milan.reservation.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

/**
 * @author Milan Rathod
 */
@Data
@Builder
public class RouteInfoResponse {
    private String stationCode;

    private String stationName;

    private int distanceFromOrigin;

    private String arrivalTime;

    private int halt;

    private int sequence;
}
