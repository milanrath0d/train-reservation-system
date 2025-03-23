package com.milan.reservation.repository;

import com.milan.reservation.model.Class;
import com.milan.reservation.model.Train;
import com.milan.reservation.model.TrainFare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Milan Rathod
 */
public interface TrainFareRepository extends JpaRepository<TrainFare, Long> {

    List<TrainFare> findByTrainAndTrainClass(Train train, Class trainClass);

    List<TrainFare> findByFromStationAndToStationAndTrainClass(String fromStation, String toStation, Class trainClass);
}
