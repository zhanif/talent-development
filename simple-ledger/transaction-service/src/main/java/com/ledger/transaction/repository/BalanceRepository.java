package com.ledger.transaction.repository;

import com.ledger.transaction.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BalanceRepository extends JpaRepository<Balance, UUID> {
    Optional<Balance> findFirstByUserId(UUID userId);
}
