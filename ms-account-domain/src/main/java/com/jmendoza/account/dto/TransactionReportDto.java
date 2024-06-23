package com.jmendoza.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmendoza.account.domain.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class TransactionReportDto {
    @JsonIgnore
    public static final String DEFAULT_OFFICE = "12";

    private String transactionId;
    private String office;
    private String description;
    private BigDecimal debit;
    private BigDecimal credit;
    private Instant date;
    private String type;
    private BigDecimal balance;
    private BigDecimal previousBalance;

    public TransactionReportDto(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.office = DEFAULT_OFFICE;
        this.debit = transaction.isCredit() ? transaction.getValue() : BigDecimal.ZERO;
        this.credit = transaction.isCredit() ? BigDecimal.ZERO : transaction.getValue().abs();
        this.date = transaction.getCreatedAt();
        this.type = transaction.getType().name();
        this.balance = transaction.getBalance();
        this.previousBalance = transaction.getPreviousBalance();
    }
}
