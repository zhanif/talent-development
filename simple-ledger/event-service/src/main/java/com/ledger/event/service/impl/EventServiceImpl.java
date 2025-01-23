package com.ledger.event.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ledger.event.data.PayloadData;
import com.ledger.event.dto.request.EventRequest;
import com.ledger.event.dto.response.EventResponse;
import com.ledger.event.enums.EventType;
import com.ledger.event.model.Event;
import com.ledger.event.repository.EventRepository;
import com.ledger.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    private static final long DEFAULT_TIMESTAMP = 0L;

    @Override
    public EventResponse receive(EventRequest request) {
        var event = this.save(request);

        // update snapshot
        if (this.canUpdateSnapshot(request)) this.updateSnapshot(request);
        return modelMapper.map(event, EventResponse.class);
    }

    private Event save(EventRequest request) {
        return eventRepository.save(modelMapper.map(request, Event.class));
    }

    @SneakyThrows
    private boolean canUpdateSnapshot(EventRequest request) {
        PayloadData payloadData = this.getPayloadDataFromString(request.getPayload());
        Event latestSnapshot = this.getLatestSnapshotForUserId(UUID.fromString(payloadData.getUserId()));
        long timestamp = latestSnapshot != null ? latestSnapshot.getTimestamp().getTime() : DEFAULT_TIMESTAMP;
        int size = eventRepository.countAfterTimestamp(UUID.fromString(payloadData.getUserId()), timestamp);
        return size % 5 == 0 && size != 0;
    }

    @SneakyThrows
    private void updateSnapshot(EventRequest request) {
        PayloadData payloadData = this.getPayloadDataFromString(request.getPayload());
        BigInteger balance = this.getBalanceForUserId(UUID.fromString(payloadData.getUserId()));

        payloadData.setAmount(balance);
        request.setType(EventType.SNAPSHOT.toString());
        request.setPayload(objectMapper.writeValueAsString(payloadData));
        request.setTimestamp(new Date());
        this.save(request);
    }

    @Override
    public BigInteger getBalanceForUserId(UUID userId) {
        BigInteger balance = BigInteger.ZERO;
        long timestamp = DEFAULT_TIMESTAMP;

        // get latest snapshot
        Event latestSnapshot = this.getLatestSnapshotForUserId(userId);
        if (latestSnapshot != null) {
            PayloadData payloadData = this.getPayloadDataFromString(latestSnapshot.getPayload());
            balance = balance.add(payloadData.getAmount());
            timestamp = latestSnapshot.getTimestamp().getTime();
        }

        List<Event> events = eventRepository.findAfterTimestamp(userId, timestamp);
        for (Event e : events) {
            PayloadData data = this.getPayloadDataFromString(e.getPayload());
            if (!UUID.fromString(data.getUserId()).equals(userId)) continue;

            if (e.getType().equals(EventType.DEPOSIT.toString())) balance = balance.add(data.getAmount());
            else if (e.getType().equals(EventType.WITHDRAW.toString())) balance = balance.subtract(data.getAmount());
        }
        return balance;
    }

    private Event getLatestSnapshotForUserId(UUID userId) {
        Event event = eventRepository.findFirstSnapshotByUserId(userId).orElse(null);
        if (event != null) {
            PayloadData payloadData = this.getPayloadDataFromString(event.getPayload());
            if (!UUID.fromString(payloadData.getUserId()).equals(userId)) event = null;
        }
        return event;
    }

    @SneakyThrows
    private PayloadData getPayloadDataFromString(String payload) {
        return objectMapper.readValue(payload, PayloadData.class);
    }
}
