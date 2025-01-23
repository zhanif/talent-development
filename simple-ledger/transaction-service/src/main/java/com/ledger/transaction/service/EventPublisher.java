package com.ledger.transaction.service;

public interface EventPublisher {
    public void publishEvent(Object payload);
}
