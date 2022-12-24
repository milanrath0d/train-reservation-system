package com.milan.reservation.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author Milan Rathod
 */
@Data
@Builder
public class CoachLayoutResponse {

    private int serialNo;

    private String code;

    private String name;

    private String number;
}
