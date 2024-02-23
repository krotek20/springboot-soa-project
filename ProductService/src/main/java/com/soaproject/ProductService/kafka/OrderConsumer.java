package com.soaproject.ProductService.kafka;

import com.soaproject.OrderService.entity.OrderMessage;
import com.soaproject.ProductService.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class OrderConsumer {

    @Autowired
    private ProductService productService;

    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(OrderMessage orderMessage) {
        log.info("Order message received in Product Service: {}", orderMessage.toString());

        if (orderMessage.getOrder() != null) {
            try {
                productService.reduceQuantity(
                        orderMessage.getOrder().getProductId(),
                        orderMessage.getOrder().getQuantity()
                );
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
