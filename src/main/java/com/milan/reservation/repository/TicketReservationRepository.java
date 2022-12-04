package com.milan.reservation.repository;

import com.milan.reservation.model.TicketReservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Milan Rathod
 */
public interface TicketReservationRepository extends JpaRepository<TicketReservation, Long> {
}
