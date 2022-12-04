package com.milan.reservation.repository;

import com.milan.reservation.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Milan Rathod
 */
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findAllByStationCode(String stationCode);

    @Query("select distinct trainNumber from route where stationCode = :stationCode")
    List<Long> findTrainNumbersByStationCode(@Param("stationCode") String stationCode);

    List<Route> findAllByTrainNumberOrderBySequence(Long trainNumber);
}
