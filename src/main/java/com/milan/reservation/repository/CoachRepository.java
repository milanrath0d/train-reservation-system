package com.milan.reservation.repository;

import com.milan.reservation.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Milan Rathod
 */
public interface CoachRepository extends JpaRepository<Coach, Long> {

    @Query("select c from coach c where c.train.trainNumber =:trainNumber order by c.sequence")
    List<Coach> findAllByTrainNumber(@Param("trainNumber") Long trainNumber);
}
