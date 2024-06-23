package com.jmendoza.account.service;

import com.jmendoza.account.dto.*;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> find();

    TransactionDto find(String id);

    TransactionDto create(Long accountNumber, CreateTransactionDto dto);

    TransactionDto update(Long accountNumber, String transactionId, UpdateTransactionDto dto);

    AccountDto delete(Long accountNumber, String transactionId);
}
