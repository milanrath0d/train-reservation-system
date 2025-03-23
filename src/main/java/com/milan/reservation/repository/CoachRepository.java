package com.milan.reservation.repository;

import com.milan.reservation.model.Coach;
import com.milan.reservation.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Milan Rathod
 */
public interface CoachRepository extends JpaRepository<Coach, Long> {

    @Query("select c from Coach c where c.train.number = :trainNumber order by c.sequence")
    List<Coach> findAllByTrainNumber(@Param("trainNumber") Long trainNumber);

    @Query("select c from Coach c where c.coachNumber = :coachNumber and c.train = :train")
    Optional<Coach> findByCoachNumberAndTrain(@Param("coachNumber") String coachNumber, @Param("train") Train train);

    List<Coach> findByTrain(Train train);
}
