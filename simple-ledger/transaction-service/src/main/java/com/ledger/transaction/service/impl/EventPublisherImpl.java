package com.ledger.transaction.service.impl;

import com.ledger.transaction.service.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EventPublisherImpl implements EventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-key}")
    private String routingKey;

    @Override
    public void publishEvent(Object payload) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
        System.out.println("\n\nEvent Published with payload: " + payload);
    }
}
