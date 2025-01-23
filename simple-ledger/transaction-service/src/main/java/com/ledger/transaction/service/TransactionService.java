package com.ledger.transaction.service;

import com.ledger.transaction.dto.request.DepositRequest;
import com.ledger.transaction.dto.request.WithdrawRequest;

public interface TransactionService {
    void deposit(DepositRequest request);
    void withdraw(WithdrawRequest request);
}
