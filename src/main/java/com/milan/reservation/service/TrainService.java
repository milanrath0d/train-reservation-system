package com.milan.reservation.service;

import com.milan.reservation.model.Train;
import com.milan.reservation.model.Zone;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.repository.ZoneRepository;
import com.milan.reservation.request.AddTrainRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Milan Rathod
 */
@Service
public class TrainService {

    private final TrainRepository trainRepository;

    private final ZoneRepository zoneRepository;

    @Autowired
    public TrainService(TrainRepository trainRepository, ZoneRepository zoneRepository) {
        this.trainRepository = trainRepository;
        this.zoneRepository = zoneRepository;
    }

    public List<Train> findAllTrains() {
        return trainRepository.findAll();
    }

    public List<Train> findAllByTrainNumbers(List<Long> trainNumbers) {
        return trainRepository.findAllByTrainNumberIn(trainNumbers);
    }

    public void addTrain(AddTrainRequest addTrainRequest) {
        final String zoneCode = addTrainRequest.getZoneCode();

        final Zone zone = zoneRepository.findByZoneCode(zoneCode);

        Train train = Train.builder()
            .source(addTrainRequest.getSource())
            .destination(addTrainRequest.getDestination())
            .trainNumber(addTrainRequest.getTrainNumber())
            .trainName(addTrainRequest.getTrainName())
            .trainType(addTrainRequest.getTrainType())
            .zone(zone)
            .startTime(addTrainRequest.getStartTime())
            .endTime(addTrainRequest.getEndTime())
            .frequency(addTrainRequest.getFrequency())
            .build();

        trainRepository.save(train);
    }
}
