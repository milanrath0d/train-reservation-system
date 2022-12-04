package com.milan.reservation.repository;

import com.milan.reservation.model.TrainFare;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Milan Rathod
 */
public interface TrainFareRepository extends JpaRepository<TrainFare, Long> {
}
