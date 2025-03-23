package com.milan.reservation.enums;

/**
 * Enum for train types
 *
 * @author Milan Rathod
 */
public enum TrainType {
    RAJDHANI("RJD", "Rajdhani Express"),
    SHATABDI("SHT", "Shatabdi Express"),
    DURONTO("DRT", "Duronto Express"),
    GARIB_RATH("GRB", "Garib Rath Express"),
    SUPERFAST("SPF", "Superfast Express"),
    EXPRESS("EXP", "Express"),
    PASSENGER("PSG", "Passenger"),
    LOCAL("LOC", "Local"),
    SPECIAL("SPL", "Special"),
    MAIL("ML", "Mail");

    private final String code;
    private final String description;

    TrainType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
