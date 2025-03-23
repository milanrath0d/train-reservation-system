package com.milan.reservation.repository;

import com.milan.reservation.model.Station;
import com.milan.reservation.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Station} entity
 *
 * @author Milan Rathod
 */
public interface StationRepository extends JpaRepository<Station, Long> {

    Optional<Station> findByStationCode(String stationCode);

    List<Station> findByZone(Zone zone);

    List<Station> findByStationNameContainingIgnoreCaseOrStationCodeContainingIgnoreCase(String name, String code);

    boolean existsByStationCode(String stationCode);
}
