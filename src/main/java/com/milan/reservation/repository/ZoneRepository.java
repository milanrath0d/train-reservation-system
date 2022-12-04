package com.milan.reservation.repository;

import com.milan.reservation.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Milan Rathod
 */
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Zone findByZoneName(String zoneName);

    Zone findByZoneCode(String zoneCode);
}
