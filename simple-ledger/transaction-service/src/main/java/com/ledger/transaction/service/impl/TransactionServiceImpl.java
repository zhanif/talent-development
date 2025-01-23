package com.ledger.transaction.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ledger.transaction.data.PayloadData;
import com.ledger.transaction.dto.request.DepositRequest;
import com.ledger.transaction.dto.request.EventRequest;
import com.ledger.transaction.dto.request.WithdrawRequest;
import com.ledger.transaction.dto.response.UserResponse;
import com.ledger.transaction.enums.TransactionType;
import com.ledger.transaction.model.Balance;
import com.ledger.transaction.repository.BalanceRepository;
import com.ledger.transaction.service.EventPublisher;
import com.ledger.transaction.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransactionServiceImpl implements TransactionService {
    private final BalanceRepository balanceRepository;
    private final RestTemplate rest;
    private final ObjectMapper objectMapper;
    private final EventPublisher eventPublisher;

    @Value("${server.gateway.url}")
    private String gatewayUrl;

    @Override
    public void deposit(DepositRequest request) {
        Balance balance = balanceRepository.findFirstByUserId(UUID.fromString(request.getUserId()))
                .orElseGet(() -> {
                    ResponseEntity<?> response = rest.getForEntity(gatewayUrl + "/user-service/users/" + request.getUserId(), UserResponse.class);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return Balance.builder()
                                .userId(UUID.fromString(request.getUserId()))
                                .amount(BigInteger.ZERO)
                                .build();
                    }
                    else throw new IllegalArgumentException("Unknown user");
                });

        balance.setAmount(balance.getAmount().add(request.getAmount()));
        balanceRepository.save(balance);

        this.publishEvent(TransactionType.DEPOSIT, request.getAmount(), balance);
    }

    @Override
    public void withdraw(WithdrawRequest request) {
        Balance balance = balanceRepository.findFirstByUserId(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new EntityNotFoundException("User has no balance"));

        if (balance.getAmount().compareTo(request.getAmount()) < 0) throw new IllegalArgumentException("Insufficient balance");

        balance.setAmount(balance.getAmount().subtract(request.getAmount()));
        balanceRepository.save(balance);

        this.publishEvent(TransactionType.WITHDRAW, request.getAmount(), balance);
    }

    @SneakyThrows
    private void publishEvent(TransactionType type, BigInteger amount, Balance balanceInfo) {
        PayloadData payloadData = PayloadData.builder()
                .balanceId(balanceInfo.getId().toString())
                .userId(balanceInfo.getUserId().toString())
                .amount(amount)
                .build();

        EventRequest request = EventRequest.builder()
                .type(type.toString())
                .payload(objectMapper.writeValueAsString(payloadData))
                .timestamp(balanceInfo.getLastModifiedDate()).build();

        // komunikasi sinkron
        // rest.postForEntity(gatewayUrl + "/event-service/events", request, Void.class);

        eventPublisher.publishEvent(objectMapper.writeValueAsString(request));
    }
}
