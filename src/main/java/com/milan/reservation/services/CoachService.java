package com.milan.reservation.services;

import com.milan.reservation.responses.CoachResponse;
import java.util.List;
import java.util.Map;

public interface CoachService {

    List<CoachResponse> getAllCoaches(Long trainNumber);

    CoachResponse getCoachByNumber(Long trainNumber, String coachNumber);

    List<CoachResponse> getCoachesByClass(Long trainNumber, String classCode);

    Integer getAvailableSeats(Long trainNumber, String coachNumber);

    List<String> getAvailableBerths(Long trainNumber, String coachNumber);

    Boolean isCoachAvailable(Long trainNumber, String coachNumber);

    List<String> getCoachFacilities(Long trainNumber, String coachNumber);

    Map<String, Integer> getCoachOccupancy(Long trainNumber);
}
