package com.milan.reservation.services.impl;

import com.milan.reservation.enums.TrainType;
import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Station;
import com.milan.reservation.model.Train;
import com.milan.reservation.model.Zone;
import com.milan.reservation.repository.RouteRepository;
import com.milan.reservation.repository.StationRepository;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.repository.ZoneRepository;
import com.milan.reservation.requests.AddStationRequest;
import com.milan.reservation.responses.StationResponse;
import com.milan.reservation.responses.TrainResponse;
import com.milan.reservation.services.StationService;
import com.milan.reservation.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final ZoneRepository zoneRepository;
    private final RouteRepository routeRepository;
    private final TrainRepository trainRepository;
    private final TrainService trainService;

    @Override
    public List<StationResponse> searchStations(String query, String zoneCode) {
        List<Station> stations;
        if (zoneCode != null && !zoneCode.isEmpty()) {
            Zone zone = zoneRepository.findByZoneCode(zoneCode)
                    .orElseThrow(() -> new ResourceNotFoundException("Zone not found with code: " + zoneCode));
            stations = stationRepository.findByZone(zone);
        } else if (query != null && !query.isEmpty()) {
            stations = stationRepository.findByStationNameContainingIgnoreCaseOrStationCodeContainingIgnoreCase(
                    query, query);
        } else {
            stations = stationRepository.findAll();
        }
        return stations.stream()
                .map(this::mapToStationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StationResponse getStation(String stationCode) {
        Station station = stationRepository.findByStationCode(stationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found with code: " + stationCode));
        return mapToStationResponse(station);
    }

    @Override
    public List<TrainResponse> getTrainsAtStation(String stationCode, LocalDate date) {
        return trainService.getTrainsByStation(stationCode, date);
    }

    @Override
    @Transactional
    public StationResponse addStation(AddStationRequest request) {
        Zone zone = zoneRepository.findByZoneCode(request.getZoneCode())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with code: " + request.getZoneCode()));

        Station station = Station.builder()
                .stationName(request.getStationName())
                .stationCode(request.getStationCode())
                .zone(zone)
                .city(request.getCity())
                .state(request.getState())
                .address(request.getAddress())
                .numberOfPlatforms(request.getNumberOfPlatforms())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .contactNumber(request.getContactNumber())
                .email(request.getEmail())
                .build();

        station = stationRepository.save(station);
        return mapToStationResponse(station);
    }

    @Override
    @Transactional
    public StationResponse updateStation(String stationCode, AddStationRequest request) {
        Station station = stationRepository.findByStationCode(stationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found with code: " + stationCode));

        Zone zone = zoneRepository.findByZoneCode(request.getZoneCode())
                .orElseThrow(() -> new ResourceNotFoundException("Zone not found with code: " + request.getZoneCode()));

        station.setStationName(request.getStationName());
        station.setZone(zone);
        station.setCity(request.getCity());
        station.setState(request.getState());
        station.setAddress(request.getAddress());
        station.setNumberOfPlatforms(request.getNumberOfPlatforms());
        station.setLatitude(request.getLatitude());
        station.setLongitude(request.getLongitude());
        station.setContactNumber(request.getContactNumber());
        station.setEmail(request.getEmail());

        station = stationRepository.save(station);
        return mapToStationResponse(station);
    }

    @Override
    public List<StationResponse> getAllStations() {
        return stationRepository.findAll().stream()
                .map(this::mapToStationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getStationFacilities(String stationCode) {
        Station station = stationRepository.findByStationCode(stationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found with code: " + stationCode));

        List<String> facilities = new ArrayList<>();
        if (station.getNumberOfPlatforms() >= 5) {
            facilities.add("Food Court");
            facilities.add("Waiting Room");
            facilities.add("Cloak Room");
        }
        facilities.add("Ticket Counter");
        facilities.add("Parking");
        facilities.add("ATM");
        
        if (station.getNumberOfPlatforms() >= 3) {
            facilities.add("Elevator");
            facilities.add("Escalator");
        }
        
        return facilities;
    }

    @Override
    public Map<String, Integer> getStationStatistics(String stationCode) {
        Station station = stationRepository.findByStationCode(stationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found with code: " + stationCode));

        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("numberOfPlatforms", station.getNumberOfPlatforms());
        statistics.put("dailyTrains", routeRepository.countByStation(station));
        statistics.put("expressTrains", routeRepository.countByStationAndTrainType(station, TrainType.EXPRESS));
        statistics.put("superFastTrains", routeRepository.countByStationAndTrainType(station, TrainType.SUPERFAST));
        return statistics;
    }

    @Override
    public List<StationResponse> getConnectingStations(String stationCode) {
        Station station = stationRepository.findByStationCode(stationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found with code: " + stationCode));

        List<Station> connectingStations = routeRepository.findConnectingStations(station);
        return connectingStations.stream()
                .map(this::mapToStationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isStationActive(String stationCode) {
        Station station = stationRepository.findByStationCode(stationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Station not found with code: " + stationCode));
        
        return routeRepository.existsByStation(station);
    }

    private StationResponse mapToStationResponse(Station station) {
        return StationResponse.builder()
                .stationCode(station.getStationCode())
                .stationName(station.getStationName())
                .city(station.getCity())
                .state(station.getState())
                .address(station.getAddress())
                .numberOfPlatforms(station.getNumberOfPlatforms())
                .latitude(station.getLatitude())
                .longitude(station.getLongitude())
                .zoneCode(station.getZone() != null ? station.getZone().getZoneCode() : null)
                .contactNumber(station.getContactNumber())
                .email(station.getEmail())
                .facilities(getStationFacilities(station.getStationCode()))
                .isActive(isStationActive(station.getStationCode()))
                .build();
    }
} 