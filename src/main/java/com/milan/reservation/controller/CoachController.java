package com.milan.reservation.controller;

import com.milan.reservation.model.Class;
import com.milan.reservation.model.Coach;
import com.milan.reservation.model.Train;
import com.milan.reservation.request.AddCoachRequest;
import com.milan.reservation.response.CoachLayoutResponse;
import com.milan.reservation.service.ClassService;
import com.milan.reservation.service.CoachService;
import com.milan.reservation.service.TrainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/coach")
public class CoachController {

    private final CoachService coachService;

    private final TrainService trainService;

    private final ClassService classService;

    public CoachController(CoachService coachService, TrainService trainService, ClassService classService) {
        this.coachService = coachService;
        this.trainService = trainService;
        this.classService = classService;
    }

    @PostMapping
    public void addCoach(@RequestBody AddCoachRequest addCoachRequest) {
        final Long trainNumber = addCoachRequest.getTrainNumber();

        final String classCode = addCoachRequest.getClassCode();

        final Train train = trainService.findByTrainNumber(trainNumber);

        final Class trainClass = Optional.ofNullable(classCode)
            .map(classService::findByClassCode)
            .orElse(null);

        final Coach coach = Coach.builder()
            .train(train)
            .trainClass(trainClass)
            .seatCapacity(addCoachRequest.getSeatCapacity())
            .sequence(addCoachRequest.getSequence())
            .coachNumber(addCoachRequest.getCoachNumber())
            .build();

        coachService.add(coach);
    }

    @GetMapping("/getByTrainNumber")
    public List<CoachLayoutResponse> getCoachLayoutByTrainNumber(@RequestParam Long trainNumber) {
        return coachService.getCoachesByTrainNumber(trainNumber)
            .stream()
            .map(coach -> CoachLayoutResponse
                .builder()
                .serialNo(coach.getSequence())
                .name(Optional.ofNullable(coach.getTrainClass()).map(Class::getClassName).orElse("Engine"))
                .code(Optional.ofNullable(coach.getTrainClass()).map(Class::getClassCode).orElse("ENG"))
                .number(coach.getCoachNumber())
                .build())
            .collect(Collectors.toList());
    }
}
