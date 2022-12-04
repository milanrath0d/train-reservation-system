package com.milan.reservation.repository;

import com.milan.reservation.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Milan Rathod
 */
public interface StationRepository extends JpaRepository<Station, Long> {
}
