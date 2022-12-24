package com.milan.reservation.service;

import com.milan.reservation.model.Coach;
import com.milan.reservation.repository.CoachRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Milan Rathod
 */
@Service
public class CoachService {

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public void add(Coach coach) {
        coachRepository.save(coach);
    }

    public List<Coach> getCoachesByTrainNumber(Long trainNumber) {
        return coachRepository.findAllByTrainNumber(trainNumber);
    }
}
