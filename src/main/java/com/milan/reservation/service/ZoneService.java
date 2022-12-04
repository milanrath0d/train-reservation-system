package com.milan.reservation.service;

import com.milan.reservation.model.Zone;
import com.milan.reservation.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Milan Rathod
 */
@Service
public class ZoneService {

    private final ZoneRepository zoneRepository;

    @Autowired
    public ZoneService(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public void addZone(Zone zone) {
        zoneRepository.save(zone);
    }
}
