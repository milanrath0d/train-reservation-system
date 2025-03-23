package com.milan.reservation.controllers;

import com.milan.reservation.requests.InitiatePaymentRequest;
import com.milan.reservation.requests.RefundRequest;
import com.milan.reservation.responses.PaymentResponse;
import com.milan.reservation.responses.RefundResponse;
import com.milan.reservation.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * @author Milan Rathod
 */
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponse> initiatePayment(@RequestBody @Valid InitiatePaymentRequest request) {
        return ResponseEntity.ok(paymentService.initiatePayment(request));
    }

    @PostMapping("/callback")
    public ResponseEntity<Void> paymentCallback(@RequestBody String payload) {
        paymentService.handlePaymentCallback(payload);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<PaymentResponse> getPaymentStatus(@PathVariable String transactionId) {
        return ResponseEntity.ok(paymentService.getPaymentStatus(transactionId));
    }

    @PostMapping("/refund")
    public ResponseEntity<RefundResponse> initiateRefund(@RequestBody @Valid RefundRequest request) {
        return ResponseEntity.ok(paymentService.initiateRefund(request));
    }

    @GetMapping("/refund/{refundId}")
    public ResponseEntity<RefundResponse> getRefundStatus(@PathVariable String refundId) {
        return ResponseEntity.ok(paymentService.getRefundStatus(refundId));
    }
} 