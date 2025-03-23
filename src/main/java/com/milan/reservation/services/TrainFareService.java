package com.milan.reservation.services;

import com.milan.reservation.requests.UpdateFareRequest;
import com.milan.reservation.responses.FareResponse;

import java.util.List;

public interface TrainFareService {

    List<FareResponse> getTrainFares(Long trainNumber, String classCode);

    List<FareResponse> getRouteFares(String sourceStation, String destinationStation, String classCode);

    List<FareResponse> updateTrainFares(Long trainNumber, UpdateFareRequest request);

    void bulkUpdateFares(List<UpdateFareRequest> requests);
}
