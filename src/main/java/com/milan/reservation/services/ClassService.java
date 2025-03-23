package com.milan.reservation.services;

import com.milan.reservation.responses.ClassResponse;
import java.util.List;

public interface ClassService {

    List<ClassResponse> getAllClasses();

    ClassResponse getClassByCode(String classCode);

    List<ClassResponse> getClassesByTrain(Long trainNumber);

    List<ClassResponse> getAvailableClasses(Long trainNumber, String sourceStation, String destinationStation);

    Boolean isClassAvailable(Long trainNumber, String classCode);

    Integer getAvailableSeats(Long trainNumber, String classCode);

    Integer getWaitingListCount(Long trainNumber, String classCode);

    List<String> getClassFacilities(String classCode);
}
