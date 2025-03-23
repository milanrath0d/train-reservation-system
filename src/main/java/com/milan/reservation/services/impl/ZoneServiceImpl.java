package com.milan.reservation.services.impl;

import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Station;
import com.milan.reservation.model.Train;
import com.milan.reservation.model.Zone;
import com.milan.reservation.repository.RouteRepository;
import com.milan.reservation.repository.StationRepository;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.repository.ZoneRepository;
import com.milan.reservation.responses.ZoneResponse;
import com.milan.reservation.services.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final StationRepository stationRepository;
    private final TrainRepository trainRepository;
    private final RouteRepository routeRepository;

    @Override
    public List<ZoneResponse> getAllZones() {
        return zoneRepository.findAll().stream()
                .map(this::mapToZoneResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ZoneResponse getZoneByCode(String zoneCode) {
        Zone zone = zoneRepository.findByZoneCode(zoneCode)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with code: " + zoneCode));
        return mapToZoneResponse(zone);
    }

    @Override
    public List<ZoneResponse> getActiveZones() {
        return zoneRepository.findAll().stream()
                .filter(zone -> !zone.getStations().isEmpty())
                .map(this::mapToZoneResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getZoneStations(String zoneCode) {
        Zone zone = zoneRepository.findByZoneCode(zoneCode)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with code: " + zoneCode));
        return zone.getStations().stream()
                .map(Station::getStationCode)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getZoneRoutes(String zoneCode) {
        Zone zone = zoneRepository.findByZoneCode(zoneCode)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with code: " + zoneCode));
        
        return zone.getTrains().stream()
                .flatMap(train -> {
                    List<Station> stations = routeRepository.findAllByTrainNumberOrderBySequence(train.getNumber())
                            .stream()
                            .map(route -> route.getStation())
                            .collect(Collectors.toList());
                    if (stations.size() >= 2) {
                        return List.of(stations.get(0).getStationCode() + "-" + 
                                     stations.get(stations.size() - 1).getStationCode()).stream();
                    }
                    return List.<String>of().stream();
                })
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getZoneTrains(String zoneCode) {
        Zone zone = zoneRepository.findByZoneCode(zoneCode)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with code: " + zoneCode));
        return zone.getTrains().stream()
                .map(Train::getNumber)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> getZoneStatistics(String zoneCode) {
        Zone zone = zoneRepository.findByZoneCode(zoneCode)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with code: " + zoneCode));

        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("totalStations", zone.getStations().size());
        statistics.put("totalTrains", zone.getTrains().size());
        statistics.put("majorStations", (int) zone.getStations().stream()
                .filter(station -> station.getNumberOfPlatforms() >= 5)
                .count());
        statistics.put("expressTrains", (int) zone.getTrains().stream()
                .filter(train -> train.getType().getCode().equals("EXP"))
                .count());
        statistics.put("superFastTrains", (int) zone.getTrains().stream()
                .filter(train -> train.getType().getCode().equals("SF"))
                .count());
        return statistics;
    }

    @Override
    public Boolean isStationInZone(String zoneCode, String stationCode) {
        Zone zone = zoneRepository.findByZoneCode(zoneCode)
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with code: " + zoneCode));
        return zone.getStations().stream()
                .anyMatch(station -> station.getStationCode().equals(stationCode));
    }

    private ZoneResponse mapToZoneResponse(Zone zone) {
        return ZoneResponse.builder()
                .zoneCode(zone.getZoneCode())
                .zoneName(zone.getZoneName())
                .region(zone.getRegion())
                .headquarters(zone.getHeadquarters())
                .totalStations(zone.getStations().size())
                .totalTrains(zone.getTrains().size())
                .stations(zone.getStations().stream()
                        .map(Station::getStationCode)
                        .collect(Collectors.toList()))
                .isActive(!zone.getStations().isEmpty())
                .build();
    }
} 