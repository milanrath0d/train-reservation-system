package com.milan.reservation.controller;

import com.milan.reservation.request.AddTrainRequest;
import com.milan.reservation.response.TrainResponse;
import com.milan.reservation.service.RouteService;
import com.milan.reservation.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/train")
public class TrainController {

    private final TrainService trainService;

    private final RouteService routeService;

    @Autowired
    public TrainController(TrainService trainService, RouteService routeService) {
        this.trainService = trainService;
        this.routeService = routeService;
    }

    @GetMapping
    public List<TrainResponse> searchTrainsByStations(@RequestParam String sourceStationCode,
                                                      @RequestParam String destinationStationCode) {
        final List<Long> sourceTrainCodes = routeService.findTrainCodesByStationCode(sourceStationCode);

        final List<Long> destinationTrainCodes = routeService.findTrainCodesByStationCode(destinationStationCode);

        sourceTrainCodes.retainAll(destinationTrainCodes);

        return trainService.findAllByTrainNumbers(sourceTrainCodes)
            .stream()
            .map(train -> TrainResponse.builder()
                .sourceStation(train.getSource())
                .destinationStation(train.getDestination())
                .trainNumber(train.getTrainNumber())
                .trainName(train.getTrainName())
                .startTime(train.getStartTime().toString())
                .endTime(train.getEndTime().toString())
                .frequency(train.getFrequency())
                .build())
            .collect(Collectors.toList());
    }

    @PostMapping
    public void addTrain(@RequestBody AddTrainRequest addTrainRequest) {
        trainService.addTrain(addTrainRequest);
    }
}
