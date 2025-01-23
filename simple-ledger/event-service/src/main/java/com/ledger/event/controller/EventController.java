package com.ledger.event.controller;

import com.ledger.event.dto.request.EventRequest;
import com.ledger.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.UUID;

@RestController
@RequestMapping("events")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<?> receive(@RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok(eventService.receive(eventRequest));
    }

    @GetMapping("/currentBalance/{userId}")
    public ResponseEntity<BigInteger> getCurrentBalance(@PathVariable String userId) {
        return ResponseEntity.ok(eventService.getBalanceForUserId(UUID.fromString(userId)));
    }
}
