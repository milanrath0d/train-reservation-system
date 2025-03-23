package com.milan.reservation.services.impl;

import com.milan.reservation.enums.BookingStatus;
import com.milan.reservation.enums.PaymentStatus;
import com.milan.reservation.exceptions.PaymentException;
import com.milan.reservation.exceptions.ResourceNotFoundException;
import com.milan.reservation.model.Payment;
import com.milan.reservation.model.Refund;
import com.milan.reservation.model.TrainBooking;
import com.milan.reservation.repository.PaymentRepository;
import com.milan.reservation.repository.RefundRepository;
import com.milan.reservation.repository.TrainBookingRepository;
import com.milan.reservation.requests.InitiatePaymentRequest;
import com.milan.reservation.requests.RefundRequest;
import com.milan.reservation.responses.PaymentResponse;
import com.milan.reservation.responses.RefundResponse;
import com.milan.reservation.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;
    private final TrainBookingRepository trainBookingRepository;

    @Override
    public PaymentResponse initiatePayment(InitiatePaymentRequest request) {
        // Validate booking reference
        TrainBooking booking = trainBookingRepository.findByPnrNumber(request.getBookingReference())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with reference: " + request.getBookingReference()));
        
        // Check if booking is in a valid state for payment
        if (booking.getStatus() != BookingStatus.PENDING && booking.getStatus() != BookingStatus.PAYMENT_PENDING) {
            throw new PaymentException("Booking is not in a valid state for payment. Current status: " + booking.getStatus());
        }
        
        // Generate unique transaction ID
        String transactionId = UUID.randomUUID().toString();

        // Create payment record
        Payment payment = Payment.builder()
                .transactionId(transactionId)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status("PENDING")
                .bookingId(booking.getId().toString())
                .paymentMethod(request.getPaymentMethod())
                .createdAt(LocalDateTime.now())
                .build();

        payment = paymentRepository.save(payment);

        // In a real implementation, you would integrate with a payment gateway here
        // For now, we'll simulate a successful payment
        payment.setStatus("SUCCESS");
        payment.setUpdatedAt(LocalDateTime.now());
        payment = paymentRepository.save(payment);
        
        // Update booking status
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPaymentStatus(PaymentStatus.COMPLETED);
        booking.setTransactionId(transactionId);
        trainBookingRepository.save(booking);

        return PaymentResponse.builder()
                .transactionId(payment.getTransactionId())
                .bookingReference(booking.getPnrNumber())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    @Override
    public void handlePaymentCallback(String payload) {
        // In a real implementation, this would handle callbacks from payment gateway
        // Parse the payment callback data
        // For example: PaymentCallbackData callbackData = parseCallbackData(payload);
        
        // Find the payment record
        // Optional<Payment> payment = paymentRepository.findByTransactionId(callbackData.getTransactionId());
        
        // Update payment status based on callback
        // if (payment.isPresent()) {
        //     Payment paymentEntity = payment.get();
        //     paymentEntity.setStatus(callbackData.getStatus());
        //     paymentEntity.setUpdatedAt(LocalDateTime.now());
        //     paymentRepository.save(paymentEntity);
        //     
        //     // Update booking status
        //     Optional<TrainBooking> booking = trainBookingRepository.findById(Long.valueOf(paymentEntity.getBookingId()));
        //     if (booking.isPresent()) {
        //         TrainBooking bookingEntity = booking.get();
        //         if ("SUCCESS".equals(callbackData.getStatus())) {
        //             bookingEntity.setStatus(BookingStatus.CONFIRMED);
        //             bookingEntity.setPaymentStatus(PaymentStatus.COMPLETED);
        //         } else {
        //             bookingEntity.setStatus(BookingStatus.PAYMENT_FAILED);
        //             bookingEntity.setPaymentStatus(PaymentStatus.FAILED);
        //         }
        //         trainBookingRepository.save(bookingEntity);
        //     }
        // }
        
        // For now, we'll just log the payload
        System.out.println("Received payment callback: " + payload);
    }

    @Override
    public PaymentResponse getPaymentStatus(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with transaction ID: " + transactionId));

        // Get booking reference for the response
        String bookingReference = "";
        Optional<TrainBooking> booking = trainBookingRepository.findById(Long.valueOf(payment.getBookingId()));
        if (booking.isPresent()) {
            bookingReference = booking.get().getPnrNumber();
        }

        return PaymentResponse.builder()
                .transactionId(payment.getTransactionId())
                .bookingReference(bookingReference)
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    @Override
    public RefundResponse initiateRefund(RefundRequest request) {
        Payment payment = paymentRepository.findByTransactionId(request.getTransactionId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with transaction ID: " + request.getTransactionId()));

        if (!"SUCCESS".equals(payment.getStatus())) {
            throw new PaymentException("Cannot initiate refund for payment that is not successful");
        }

        // Find the booking
        Optional<TrainBooking> bookingOpt = trainBookingRepository.findById(Long.valueOf(payment.getBookingId()));
        if (bookingOpt.isEmpty()) {
            throw new ResourceNotFoundException("Booking not found for payment ID: " + payment.getBookingId());
        }
        
        TrainBooking booking = bookingOpt.get();
        
        // Update booking status
        booking.setStatus(BookingStatus.REFUND_INITIATED);
        booking.setRefundAmount(new BigDecimal(request.getRefundAmount().toString()));
        trainBookingRepository.save(booking);

        String refundId = UUID.randomUUID().toString();

        Refund refund = Refund.builder()
                .refundId(refundId)
                .payment(payment)
                .amount(request.getRefundAmount())
                .reason(request.getRefundReason())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        refund = refundRepository.save(refund);

        // In a real implementation, you would integrate with a payment gateway here
        // For now, we'll simulate a successful refund
        refund.setStatus("SUCCESS");
        refund.setUpdatedAt(LocalDateTime.now());
        refund = refundRepository.save(refund);
        
        // Update booking status after successful refund
        booking.setStatus(BookingStatus.REFUND_COMPLETED);
        booking.setRefundTransactionId(refundId);
        booking.setPaymentStatus(PaymentStatus.REFUNDED);
        trainBookingRepository.save(booking);

        return RefundResponse.builder()
                .refundId(refund.getRefundId())
                .amount(refund.getAmount())
                .status(refund.getStatus())
                .createdAt(refund.getCreatedAt())
                .build();
    }

    @Override
    public RefundResponse getRefundStatus(String refundId) {
        Refund refund = refundRepository.findByRefundId(refundId)
                .orElseThrow(() -> new ResourceNotFoundException("Refund not found with ID: " + refundId));

        return RefundResponse.builder()
                .refundId(refund.getRefundId())
                .amount(refund.getAmount())
                .status(refund.getStatus())
                .createdAt(refund.getCreatedAt())
                .build();
    }
} 