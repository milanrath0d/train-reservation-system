package com.milan.reservation.controller;

import com.milan.reservation.model.Zone;
import com.milan.reservation.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/zone")
public class ZoneController {

    private final ZoneService zoneService;

    @Autowired
    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @PostMapping()
    public void addZone(@RequestParam String zoneName, @RequestParam String zoneCode) {
        zoneService.addZone(Zone.builder()
            .zoneName(zoneName)
            .zoneCode(zoneCode)
            .build());
    }
}
