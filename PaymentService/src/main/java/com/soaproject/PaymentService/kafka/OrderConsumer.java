package com.soaproject.PaymentService.kafka;

import com.soaproject.OrderService.entity.OrderMessage;
import com.soaproject.PaymentService.model.PaymentRequest;
import com.soaproject.PaymentService.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderConsumer {

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderMessage orderMessage) {
        log.info("Order message received in Payment Service: {}", orderMessage.toString());

        if (orderMessage.getOrder() != null) {
            try {
                paymentService.doPayment(
                        PaymentRequest.builder()
                                .orderId(orderMessage.getOrder().getId())
                                .paymentMode(orderMessage.getPaymentMode())
                                .amount(orderMessage.getOrder().getAmount())
                                .build()
                );
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
