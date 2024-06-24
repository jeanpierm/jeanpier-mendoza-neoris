package com.jmendoza.account.service;

import com.jmendoza.account.dto.AccountDto;
import com.jmendoza.account.dto.CreateTransactionDto;
import com.jmendoza.account.dto.TransactionDto;
import com.jmendoza.account.dto.UpdateTransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> find();

    TransactionDto find(String id);

    TransactionDto create(Long accountNumber, CreateTransactionDto dto);

    TransactionDto update(Long accountNumber, String transactionId, UpdateTransactionDto dto);

    AccountDto delete(Long accountNumber, String transactionId);
}
