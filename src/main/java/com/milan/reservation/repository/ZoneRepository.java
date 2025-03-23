package com.milan.reservation.repository;

import com.milan.reservation.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link Zone} entity
 *
 * @author Milan Rathod
 */
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Optional<Zone> findByZoneCode(String zoneCode);

    boolean existsByZoneCode(String zoneCode);
}
