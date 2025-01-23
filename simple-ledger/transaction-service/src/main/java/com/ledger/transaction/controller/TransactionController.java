package com.ledger.transaction.controller;

import com.ledger.transaction.dto.request.DepositRequest;
import com.ledger.transaction.dto.request.WithdrawRequest;
import com.ledger.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@Valid @RequestBody DepositRequest request) {
        transactionService.deposit(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@Valid @RequestBody WithdrawRequest request) {
        transactionService.withdraw(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
