package com.jmendoza.account.dto;

import java.util.List;

public record FindTransactionsDto(List<TransactionDto> transactions) {
}
