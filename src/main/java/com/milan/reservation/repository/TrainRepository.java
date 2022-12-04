package com.milan.reservation.repository;

import com.milan.reservation.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Milan Rathod
 */
public interface TrainRepository extends JpaRepository<Train, Long> {

    List<Train> findAllByTrainNumberIn(List<Long> trainNumbers);

}
