package com.soaproject.OrderService.service;

import com.soaproject.OrderService.model.OrderRequest;
import com.soaproject.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
