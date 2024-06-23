package com.jmendoza.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
public class AccountsReportDto {
    private CustomerReportDto customer;
    private List<AccountReportDto> accounts;
    private Instant generatedAt = Instant.now();

    public AccountsReportDto(CustomerDto customer, List<AccountReportDto> accounts) {
        this.customer = new CustomerReportDto(customer);
        this.accounts = accounts;
    }
}
