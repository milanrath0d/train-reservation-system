package com.milan.reservation.repository;

import com.milan.reservation.enums.BookingStatus;
import com.milan.reservation.model.Train;
import com.milan.reservation.model.TrainBooking;
import com.milan.reservation.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Milan Rathod
 */
public interface TrainBookingRepository extends JpaRepository<TrainBooking, Long> {

    Optional<TrainBooking> findByPnrNumber(String pnrNumber);

    List<TrainBooking> findByUserAndJourneyDateBetween(User user, LocalDate fromDate, LocalDate toDate);

    List<TrainBooking> findByStatus(BookingStatus status);

    List<TrainBooking> findByTrainAndJourneyDate(Train train, LocalDate journeyDate);
}
