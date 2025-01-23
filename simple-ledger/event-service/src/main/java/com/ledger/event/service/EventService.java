package com.ledger.event.service;

import com.ledger.event.dto.request.EventRequest;
import com.ledger.event.dto.response.EventResponse;

import java.math.BigInteger;
import java.util.UUID;

public interface EventService {
    EventResponse receive(EventRequest request);
    BigInteger getBalanceForUserId(UUID userId);
}
