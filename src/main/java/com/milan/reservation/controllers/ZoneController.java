package com.milan.reservation.controllers;

import com.milan.reservation.responses.ZoneResponse;
import com.milan.reservation.services.ZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/zones")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping
    public ResponseEntity<List<ZoneResponse>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

    @GetMapping("/{zoneCode}")
    public ResponseEntity<ZoneResponse> getZoneByCode(@PathVariable String zoneCode) {
        return ResponseEntity.ok(zoneService.getZoneByCode(zoneCode));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ZoneResponse>> getActiveZones() {
        return ResponseEntity.ok(zoneService.getActiveZones());
    }

    @GetMapping("/{zoneCode}/stations")
    public ResponseEntity<List<String>> getZoneStations(@PathVariable String zoneCode) {
        return ResponseEntity.ok(zoneService.getZoneStations(zoneCode));
    }

    @GetMapping("/{zoneCode}/routes")
    public ResponseEntity<List<String>> getZoneRoutes(@PathVariable String zoneCode) {
        return ResponseEntity.ok(zoneService.getZoneRoutes(zoneCode));
    }

    @GetMapping("/{zoneCode}/trains")
    public ResponseEntity<List<Long>> getZoneTrains(@PathVariable String zoneCode) {
        return ResponseEntity.ok(zoneService.getZoneTrains(zoneCode));
    }

    @GetMapping("/{zoneCode}/statistics")
    public ResponseEntity<Map<String, Integer>> getZoneStatistics(@PathVariable String zoneCode) {
        return ResponseEntity.ok(zoneService.getZoneStatistics(zoneCode));
    }

    @GetMapping("/{zoneCode}/stations/{stationCode}/check")
    public ResponseEntity<Boolean> isStationInZone(
            @PathVariable String zoneCode,
            @PathVariable String stationCode) {
        return ResponseEntity.ok(zoneService.isStationInZone(zoneCode, stationCode));
    }
}
