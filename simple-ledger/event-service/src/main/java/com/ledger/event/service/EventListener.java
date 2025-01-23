package com.ledger.event.service;

public interface EventListener {
    public void handleEvent(String payload);
}
