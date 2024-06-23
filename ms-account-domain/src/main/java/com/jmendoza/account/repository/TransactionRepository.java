package com.jmendoza.account.repository;

import com.jmendoza.account.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByAccountNumberAndCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(Long accountNumber, Instant startDate, Instant endDate);
}
