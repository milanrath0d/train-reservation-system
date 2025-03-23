package com.milan.reservation.services;

import com.milan.reservation.requests.InitiatePaymentRequest;
import com.milan.reservation.requests.RefundRequest;
import com.milan.reservation.responses.PaymentResponse;
import com.milan.reservation.responses.RefundResponse;

public interface PaymentService {

    PaymentResponse initiatePayment(InitiatePaymentRequest request);

    void handlePaymentCallback(String payload);

    PaymentResponse getPaymentStatus(String transactionId);

    RefundResponse initiateRefund(RefundRequest request);

    RefundResponse getRefundStatus(String refundId);
} 