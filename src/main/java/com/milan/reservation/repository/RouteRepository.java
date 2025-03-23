package com.milan.reservation.repository;

import com.milan.reservation.enums.TrainType;
import com.milan.reservation.model.Route;
import com.milan.reservation.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for {@link Route} entity
 *
 * @author Milan Rathod
 */
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("SELECT r FROM Route r WHERE r.train.number = :trainNumber ORDER BY r.sequence")
    List<Route> findAllByTrainNumberOrderBySequence(@Param("trainNumber") Long trainNumber);

    @Query("SELECT DISTINCT r.train.number FROM Route r WHERE r.station.stationCode = :stationCode")
    List<Long> findTrainNumbersByStationCode(@Param("stationCode") String stationCode);

    int countByStation(Station station);

    @Query("SELECT COUNT(DISTINCT r) FROM Route r WHERE r.station = :station AND r.train.type = :trainType")
    int countByStationAndTrainType(@Param("station") Station station, @Param("trainType") TrainType trainType);

    boolean existsByStation(Station station);

    @Query("SELECT DISTINCT r2.station FROM Route r1, Route r2 " +
            "WHERE r1.train = r2.train AND r1.station = :station AND r2.station != :station " +
            "AND r2.sequence > r1.sequence ORDER BY r2.sequence")
    List<Station> findConnectingStations(@Param("station") Station station);
}
