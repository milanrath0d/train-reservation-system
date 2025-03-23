package com.milan.reservation.repository;

import com.milan.reservation.model.Train;
import com.milan.reservation.model.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for {@link TrainSchedule} entity
 *
 * @author Milan Rathod
 */
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {

    @Query("SELECT ts FROM TrainSchedule ts WHERE ts.train = :train " +
            "AND ts.effectiveFrom <= :date AND (ts.effectiveTo IS NULL OR ts.effectiveTo >= :date)")
    Optional<TrainSchedule> findByTrainAndDate(@Param("train") Train train, @Param("date") LocalDate date);
} 