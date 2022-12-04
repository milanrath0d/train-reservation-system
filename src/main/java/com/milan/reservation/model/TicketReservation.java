package com.milan.reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author Milan Rathod
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ticket_reservation")
public class TicketReservation {

    @Id
    private Long id;

    private Long pnrNumber;

    private String fromStation;

    private String toStation;

    private String stationCode;

    private int fromKm;

    private int toKm;

    private LocalDate fromDate;

    private LocalDate toDate;
}
