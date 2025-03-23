package com.milan.reservation.repository;

import com.milan.reservation.enums.BookingStatus;
import com.milan.reservation.model.TrainBooking;
import com.milan.reservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link TrainBooking} entity
 *
 * @author Milan Rathod
 */
public interface BookingRepository extends JpaRepository<TrainBooking, Long> {

    Optional<TrainBooking> findByPnrNumber(String pnrNumber);

    List<TrainBooking> findByUser(User user);

    @Query("SELECT COUNT(b) FROM TrainBooking b WHERE b.user = :user " +
            "AND b.journeyDate BETWEEN :fromDate AND :toDate")
    int countByUserAndDateRange(@Param("user") User user,
                              @Param("fromDate") LocalDate fromDate,
                              @Param("toDate") LocalDate toDate);

    @Query("SELECT COUNT(b) FROM TrainBooking b WHERE b.user = :user " +
            "AND b.status = :status AND b.journeyDate BETWEEN :fromDate AND :toDate")
    int countByUserAndStatusAndDateRange(@Param("user") User user,
                                       @Param("status") BookingStatus status,
                                       @Param("fromDate") LocalDate fromDate,
                                       @Param("toDate") LocalDate toDate);

    List<TrainBooking> findByUserAndJourneyDateBetween(User user, LocalDate fromDate, LocalDate toDate);

    List<TrainBooking> findByStatus(BookingStatus status);

    List<TrainBooking> findByTrainNumberAndJourneyDate(Long trainNumber, LocalDate journeyDate);
} 