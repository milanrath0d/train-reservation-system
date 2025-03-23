package com.milan.reservation.request;

import lombok.Data;

/**
 * @author Milan Rathod
 */
@Data
public class AddCoachRequest {

    private String coachNumber;

    private String classCode;

    private int seatCapacity;

    private Long trainNumber;

    private int sequence;
}
