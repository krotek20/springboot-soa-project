package com.soaproject.PaymentService.service;

import com.soaproject.PaymentService.model.PaymentRequest;
import com.soaproject.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
