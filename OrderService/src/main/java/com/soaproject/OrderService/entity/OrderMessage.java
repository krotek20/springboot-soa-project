package com.soaproject.OrderService.entity;

import com.soaproject.OrderService.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMessage {
    private String message;
    private String status;
    private Order order;
    private PaymentMode paymentMode;
}
