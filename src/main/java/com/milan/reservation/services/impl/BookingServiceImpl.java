package com.milan.reservation.services.impl;

import com.milan.reservation.enums.BookingSource;
import com.milan.reservation.enums.BookingStatus;
import com.milan.reservation.enums.Gender;
import com.milan.reservation.enums.PaymentStatus;
import com.milan.reservation.enums.SeatStatus;
import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Class;
import com.milan.reservation.model.Passenger;
import com.milan.reservation.model.Train;
import com.milan.reservation.model.TrainBooking;
import com.milan.reservation.model.User;
import com.milan.reservation.repository.ClassRepository;
import com.milan.reservation.repository.TrainBookingRepository;
import com.milan.reservation.repository.TrainRepository;
import com.milan.reservation.repository.UserRepository;
import com.milan.reservation.requests.BookingRequest;
import com.milan.reservation.responses.BookingResponse;
import com.milan.reservation.responses.PassengerResponse;
import com.milan.reservation.responses.PnrStatusResponse;
import com.milan.reservation.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final TrainBookingRepository bookingRepository;
    private final TrainRepository trainRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        Train train = trainRepository.findByNumber(request.getTrainNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + request.getTrainNumber()));

        Class trainClass = classRepository.findByClassCode(request.getClassCode())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found: " + request.getClassCode()));

        final User user = userRepository.findByUsername(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));

        // Generate PNR
        String pnr = generatePNR(train.getNumber());

        // Calculate fares
        BigDecimal totalFare = calculateFare(request);
        BigDecimal taxAmount = calculateTax(totalFare);
        BigDecimal finalAmount = totalFare.add(taxAmount);

        // Create booking
        final TrainBooking booking = TrainBooking.builder()
                .pnrNumber(pnr)
                .train(train)
                .user(user)
                .emailId(user.getEmail())
                .trainClass(trainClass)
                .sourceStation(request.getSourceStation())
                .destinationStation(request.getDestinationStation())
                .journeyDate(request.getJourneyDate())
                .departureTime(train.getStartTime())
                .arrivalTime(train.getEndTime())
                .totalFare(totalFare)
                .taxAmount(taxAmount)
                .finalAmount(finalAmount)
                .status(BookingStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .bookingSource(request.getBookingSource() != null ? BookingSource.valueOf(request.getBookingSource()) : BookingSource.WEB)
                .bookingDate(LocalDateTime.now())
                .build();

        // Create passengers
        List<Passenger> passengers = request.getPassengers().stream()
                .map(p -> {
                    Passenger passenger = Passenger.builder()
                            .trainBooking(booking)
                            .name(p.getName())
                            .age(p.getAge())
                            .gender(Gender.valueOf(p.getGender()))
                            .isLeadPassenger(p.isLeadPassenger())
                            .seatStatus(SeatStatus.AVAILABLE)
                            .idType(p.getIdProofType())
                            .idNumber(p.getIdProofNumber())
                            .mealPreference(p.getMealPreference())
                            .specialAssistance(p.getSpecialRequests())
                            .build();
                    return passenger;
                })
                .collect(Collectors.toList());

        booking.setPassengers(passengers);
        return mapToBookingResponse(bookingRepository.save(booking));
    }

    @Override
    public BookingResponse getBookingByPnr(String pnrNumber) {
        TrainBooking booking = bookingRepository.findByPnrNumber(pnrNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnrNumber));
        return mapToBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getUserBookings(String userId, LocalDate fromDate, LocalDate toDate) {
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        List<TrainBooking> bookings = bookingRepository.findByUserAndJourneyDateBetween(user, fromDate, toDate);
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByStatus(BookingStatus status) {
        List<TrainBooking> bookings = bookingRepository.findByStatus(status);
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PnrStatusResponse getPnrStatus(String pnrNumber) {
        TrainBooking booking = bookingRepository.findByPnrNumber(pnrNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnrNumber));

        return PnrStatusResponse.builder()
                .pnrNumber(booking.getPnrNumber())
                .trainNumber(booking.getTrain().getNumber())
                .trainName(booking.getTrain().getName())
                .journeyDate(booking.getJourneyDate())
                .sourceStation(booking.getSourceStation())
                .destinationStation(booking.getDestinationStation())
                .bookingStatus(booking.getStatus())
                .passengers(booking.getPassengers().stream()
                        .map(p -> PassengerResponse.builder()
                                .name(p.getName())
                                .age(p.getAge())
                                .gender(p.getGender().toString())
                                .seatNumber(p.getSeatNumber())
                                .status(p.getSeatStatus().toString())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public BookingResponse cancelBooking(String pnrNumber, String reason) {
        TrainBooking booking = bookingRepository.findByPnrNumber(pnrNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnrNumber));

        if (!isBookingCancellable(pnrNumber)) {
            throw new IllegalStateException("Booking cannot be cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(reason);
        booking.setCancellationDate(LocalDateTime.now());
        booking = bookingRepository.save(booking);

        return mapToBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getBookingsByTrain(Long trainNumber, LocalDate journeyDate) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));
        List<TrainBooking> bookings = bookingRepository.findByTrainAndJourneyDate(train, journeyDate);
        return bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> getBookingStatistics(Long trainNumber, LocalDate journeyDate) {
        Train train = trainRepository.findByNumber(trainNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with number: " + trainNumber));
        List<TrainBooking> bookings = bookingRepository.findByTrainAndJourneyDate(train, journeyDate);
        
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("total", bookings.size());
        statistics.put("confirmed", countBookingsByStatus(bookings, BookingStatus.CONFIRMED));
        statistics.put("waitlisted", countBookingsByStatus(bookings, BookingStatus.WAITLISTED));
        statistics.put("cancelled", countBookingsByStatus(bookings, BookingStatus.CANCELLED));
        
        return statistics;
    }

    @Override
    public Boolean isBookingCancellable(String pnrNumber) {
        TrainBooking booking = bookingRepository.findByPnrNumber(pnrNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnrNumber));

        // Check if booking is already cancelled
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return false;
        }

        // Check if journey date has passed
        if (booking.getJourneyDate().isBefore(LocalDate.now())) {
            return false;
        }

        // Add more business rules for cancellation here
        return true;
    }

    @Override
    public BigDecimal calculateRefundAmount(String pnrNumber) {
        TrainBooking booking = bookingRepository.findByPnrNumber(pnrNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with PNR: " + pnrNumber));

        if (booking.getStatus() != BookingStatus.CANCELLED) {
            throw new IllegalStateException("Booking is not cancelled");
        }

        // Calculate refund based on cancellation time
        long daysUntilJourney = LocalDate.now().until(booking.getJourneyDate()).getDays();
        BigDecimal refundPercentage;

        if (daysUntilJourney > 7) {
            refundPercentage = new BigDecimal("0.75"); // 75% refund
        } else if (daysUntilJourney > 3) {
            refundPercentage = new BigDecimal("0.50"); // 50% refund
        } else {
            refundPercentage = new BigDecimal("0.25"); // 25% refund
        }

        return booking.getFinalAmount().multiply(refundPercentage);
    }

    /**
     * Format of PNR: YYYYMMDD-TRAIN-RANDOM
     *
     * @param trainNumber trainNumber
     * @return PNR
     */
    private String generatePNR(Long trainNumber) {
        return String.format("%s-%d-%04d",
            LocalDate.now().toString().replace("-", ""),
            trainNumber,
            new Random().nextInt(10000));
    }

    private BigDecimal calculateFare(BookingRequest request) {
        // TODO: Implement actual fare calculation logic
        return new BigDecimal("1000.00").multiply(new BigDecimal(request.getPassengers().size()));
    }

    private BigDecimal calculateTax(BigDecimal amount) {
        // TODO: Implement actual tax calculation logic
        return amount.multiply(new BigDecimal("0.18")); // 18% GST
    }

    private int countBookingsByStatus(List<TrainBooking> bookings, BookingStatus status) {
        return (int) bookings.stream()
                .filter(booking -> booking.getStatus() == status)
                .count();
    }

    private BookingResponse mapToBookingResponse(TrainBooking booking) {
        return BookingResponse.builder()
                .pnrNumber(booking.getPnrNumber())
                .trainNumber(booking.getTrain().getNumber())
                .trainName(booking.getTrain().getName())
                .trainClass(booking.getTrainClass().getClassCode())
                .sourceStation(booking.getSourceStation())
                .destinationStation(booking.getDestinationStation())
                .journeyDate(booking.getJourneyDate())
                .bookingDateTime(booking.getBookingDate())
                .bookingStatus(booking.getStatus())
                .paymentStatus(booking.getPaymentStatus().toString())
                .totalFare(booking.getTotalFare())
                .finalAmount(booking.getFinalAmount())
                .passengers(booking.getPassengers().stream()
                        .map(p -> PassengerResponse.builder()
                                .name(p.getName())
                                .age(p.getAge())
                                .gender(p.getGender().toString())
                                .seatNumber(p.getSeatNumber())
                                .status(p.getSeatStatus().toString())
                                .build())
                        .collect(Collectors.toList()))
                .transactionId(booking.getTransactionId())
                .cancellationDateTime(booking.getCancellationDate())
                .cancellationReason(booking.getCancellationReason())
                .refundAmount(booking.getRefundAmount())
                .waitingListNumber(booking.getWaitlistNumber())
                .bookingSource(booking.getBookingSource().toString())
                .build();
    }
} 