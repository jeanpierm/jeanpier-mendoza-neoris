package com.jmendoza.account.dto;

import com.jmendoza.account.core.annotations.EnumPattern;
import com.jmendoza.account.core.constraints.Post;
import com.jmendoza.account.core.constraints.Put;
import com.jmendoza.account.domain.Account;
import com.jmendoza.account.util.Strings;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
        this.number = Strings.leftPadWithZero(account.getNumber().toString(), Account.NUMBER_LENGTH_AS_STR);
        this.customerId = account.getCustomerId();
        this.type = account.getType().name();
    }
}
