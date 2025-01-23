package com.ledger.transaction.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayloadData {
    private String balanceId;
    private String userId;
    private BigInteger amount;
}
