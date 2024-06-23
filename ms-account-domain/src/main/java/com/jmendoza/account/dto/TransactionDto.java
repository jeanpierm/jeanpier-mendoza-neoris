package com.jmendoza.account.dto;

import com.jmendoza.account.domain.Transaction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
public class TransactionDto {
    private String transactionId;
    private Instant date;
    private String type;
    private BigDecimal value;
    private BigDecimal balance;
    private BigDecimal previousBalance;

    public TransactionDto(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.date = transaction.getCreatedAt();
        this.type = transaction.getType().name();
        this.value = transaction.getValue();
        this.balance = transaction.getBalance();
        this.previousBalance = transaction.getPreviousBalance();
    }
}
