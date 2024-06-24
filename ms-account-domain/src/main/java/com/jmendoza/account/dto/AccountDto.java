package com.jmendoza.account.dto;

import com.jmendoza.account.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AccountDto {
    private String number;

    private String type;

    private BigDecimal balance;

    private Boolean state;

    private String customerId;

    public AccountDto(Account account) {
        this.state = account.getState();
        this.balance = account.getBalance();
        this.number = account.getNumberFormatted();
        this.customerId = account.getCustomerId();
        this.type = account.getType().name();
    }
}
