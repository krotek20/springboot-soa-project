package com.soaproject.OrderService.service;

import com.soaproject.OrderService.entity.Order;
import com.soaproject.OrderService.entity.OrderMessage;
import com.soaproject.OrderService.exception.CustomException;
import com.soaproject.OrderService.external.response.PaymentResponse;
import com.soaproject.OrderService.external.response.ProductResponse;
import com.soaproject.OrderService.kafka.OrderProducer;
import com.soaproject.OrderService.model.OrderRequest;
import com.soaproject.OrderService.model.OrderResponse;
import com.soaproject.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing order request: {}", orderRequest);

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);

        OrderMessage orderMessage = OrderMessage.builder()
                .message("Order " + order.getId() + " is now in pending...")
                .status("PENDING")
                .order(order)
                .paymentMode(orderRequest.getPaymentMode())
                .build();

        orderProducer.sendMessage(orderMessage);

        log.info("Order placed successfully with order ID: {}", order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get order details for order ID {}", orderId);

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new CustomException(
                        "Order not found for the ID " + orderId,
                        "NOT_FOUND",
                        404));

        log.info("Invoking Product Service to fetch the product for ID {}", order.getProductId());
        ProductResponse productResponse =
                restTemplate.getForObject(
                        "http://PRODUCT-SERVICE/product/" + order.getProductId(),
                        ProductResponse.class
                );

        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
                .productName(productResponse.getProductName())
                .productId(productResponse.getProductId())
                .build();

        log.info("Getting payment information from the Payment Service");
        PaymentResponse paymentResponse =
                restTemplate.getForObject(
                        "http://PAYMENT-SERVICE/payment/order/" + orderId,
                        PaymentResponse.class
                );

        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder()
                .paymentId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .productDetails(productDetails)
                .paymentDetails(paymentDetails)
                .build();
    }
}
