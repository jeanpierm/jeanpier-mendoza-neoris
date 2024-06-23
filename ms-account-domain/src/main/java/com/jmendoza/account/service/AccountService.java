package com.jmendoza.account.service;

import com.jmendoza.account.dto.AccountDto;
import com.jmendoza.account.dto.CreateAccountDto;
import com.jmendoza.account.dto.UpdateAccountDto;

import java.util.List;

public interface AccountService {
    List<AccountDto> find();

    AccountDto find(Long id);

    AccountDto create(CreateAccountDto dto);

    AccountDto update(Long id, UpdateAccountDto dto);

    void delete(Long id);

    void deactivate(Long id);

    void activate(Long id);
}
