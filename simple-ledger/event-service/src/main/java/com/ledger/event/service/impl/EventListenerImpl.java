package com.ledger.event.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ledger.event.dto.request.EventRequest;
import com.ledger.event.service.EventListener;
import com.ledger.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EventListenerImpl implements EventListener {
    private final ObjectMapper objectMapper;
    private final EventService eventService;

    @Override
    @SneakyThrows
    @RabbitListener(queues = "event-service-queue")
    public void handleEvent(String payload) {
        System.out.println("\n\nEvent received with payload: " + payload);
        eventService.receive(objectMapper.readValue(payload, EventRequest.class));
    }
}
