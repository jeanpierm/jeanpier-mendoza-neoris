package com.jmendoza.account.dto;

import com.jmendoza.account.domain.Account;
import com.jmendoza.account.domain.Transaction;
import com.jmendoza.account.util.Strings;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountReportDto {
    private String number;
    private String type;
    private BigDecimal currentBalance;
    private List<TransactionReportDto> transactions;

    public AccountReportDto(Account account, List<Transaction> transactions) {
        this.number = account.getNumberFormatted();
        this.type = account.getType().name();
        this.currentBalance = account.getBalance();
        this.transactions = transactions.stream()
                .parallel()
                .map(TransactionReportDto::new)
                .toList();
    }
}
