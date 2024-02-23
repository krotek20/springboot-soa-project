package com.soaproject.OrderService.kafka;

import com.soaproject.OrderService.entity.OrderMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class OrderProducer {

    private NewTopic topic;
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void sendMessage(OrderMessage orderMessage) {
        log.info("Order message send: {}", orderMessage.toString());

        Message<OrderMessage> message = MessageBuilder
                .withPayload(orderMessage)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();

        kafkaTemplate.send(message);
    }
}
