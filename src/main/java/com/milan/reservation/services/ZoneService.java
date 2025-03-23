package com.milan.reservation.services;

import com.milan.reservation.responses.ZoneResponse;
import java.util.List;
import java.util.Map;

public interface ZoneService {

    List<ZoneResponse> getAllZones();

    ZoneResponse getZoneByCode(String zoneCode);

    List<ZoneResponse> getActiveZones();

    List<String> getZoneStations(String zoneCode);

    List<String> getZoneRoutes(String zoneCode);

    List<Long> getZoneTrains(String zoneCode);

    Map<String, Integer> getZoneStatistics(String zoneCode);

    Boolean isStationInZone(String zoneCode, String stationCode);
}
