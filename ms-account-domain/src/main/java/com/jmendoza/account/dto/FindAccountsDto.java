package com.jmendoza.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FindAccountsDto {
    private List<AccountDto> accounts;
}
