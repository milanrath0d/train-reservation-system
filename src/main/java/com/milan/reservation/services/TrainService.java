package com.milan.reservation.services;

import com.milan.reservation.responses.TrainResponse;
import java.time.LocalDate;
import java.util.List;

public interface TrainService {

    List<TrainResponse> searchTrains(String sourceStation, String destinationStation, LocalDate journeyDate);

    TrainResponse getTrainByNumber(Long trainNumber);

    List<TrainResponse> getTrainsByRoute(String routeCode);

    List<TrainResponse> getTrainsByStation(String stationCode, LocalDate date);

    List<String> getTrainSchedule(Long trainNumber);

    Boolean isTrainRunningOnDate(Long trainNumber, LocalDate date);

    List<String> getIntermediateStations(Long trainNumber);

    String getJourneyDuration(Long trainNumber, String sourceStation, String destinationStation);

    List<TrainResponse> getConnectingTrains(String sourceStation, String destinationStation, LocalDate date);
}
