package com.jmendoza.account.service;

import com.jmendoza.account.core.enums.ResponseDictionary;
import com.jmendoza.account.core.exception.BadRequestException;
import com.jmendoza.account.core.exception.NotFoundException;
import com.jmendoza.account.domain.Transaction;
import com.jmendoza.account.dto.AccountDto;
import com.jmendoza.account.dto.CreateTransactionDto;
import com.jmendoza.account.dto.TransactionDto;
import com.jmendoza.account.dto.UpdateTransactionDto;
import com.jmendoza.account.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountServiceImpl accountService;

    /**
     * Encontrar todas los movimientos
     *
     * @return Una lista de todos los movimientos
     */
    @Override
    public List<TransactionDto> find() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionDto::new)
                .toList();
    }

    /**
     * Encontrar un movimiento por ID
     *
     * @param id El ID del movimiento
     * @return El movimiento encontrado
     */
    @Override
    public TransactionDto find(String id) {
        var transaction = findByIdAndValidateExists(id);
        return new TransactionDto(transaction);
    }

    /**
     * Encontrar un movimiento por ID y validar que exista
     *
     * @param id El ID del movimiento
     * @return El movimiento encontrado
     */
    private Transaction findByIdAndValidateExists(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ResponseDictionary.TRANSACTION_NOT_FOUND, id.toString()));
    }

    /**
     * Crear un nuevo movimiento
     * @param accountNumber El número de cuenta
     * @param dto Los datos del movimiento
     * @return El movimiento creado
     */
    @Override
    @Transactional
    public TransactionDto create(Long accountNumber, CreateTransactionDto dto) {
        var izZero = dto.getValue().compareTo(BigDecimal.ZERO) == 0;
        if (izZero) {
            throw new BadRequestException(ResponseDictionary.TRANSACTION_CANNOT_BE_ZERO);
        }

        var account = accountService.findByNumberAndValidateExists(accountNumber);
        var transaction = new Transaction(dto.getValue(), account);

        if (!account.hasSufficientBalanceForTransaction(transaction.getValue())) {
            throw new BadRequestException(ResponseDictionary.INSUFFICIENT_BALANCE);
        }
        account.updateBalance(transaction.getValue());
        transaction = transactionRepository.save(transaction);
        log.info("Movimiento de cuenta {} creado con id {}", account.getNumber(), transaction.getTransactionId());
        return new TransactionDto(transaction);
    }

    /**
     * Actualizar un movimiento por ID
     * @param accountNumber El número de cuenta
     * @param transactionId El ID del movimiento
     * @param dto Los datos del movimiento
     * @return El movimiento actualizado
     */
    @Override
    @Transactional
    public TransactionDto update(Long accountNumber, String transactionId, UpdateTransactionDto dto) {
        var izZero = dto.getValue().compareTo(BigDecimal.ZERO) == 0;
        if (izZero) {
            throw new BadRequestException(ResponseDictionary.TRANSACTION_CANNOT_BE_ZERO);
        }

        var account = accountService.findByNumberAndValidateExists(accountNumber);
        var transaction = findByIdAndValidateExists(transactionId);

        account.setBalance(transaction.getPreviousBalance()); // establece saldo previo de la transaccion
        if (!account.hasSufficientBalanceForTransaction(dto.getValue())) {
            throw new BadRequestException(ResponseDictionary.INSUFFICIENT_BALANCE);
        }
        transaction.setPreviousBalance(account.getBalance());

        account.updateBalance(dto.getValue());
        transaction.setValue(dto.getValue());
        transaction.setBalance(account.getBalance());

        log.info("Movimiento de cuenta {} actualizado con id {}", account.getNumber(), transaction.getTransactionId());
        return new TransactionDto(transaction);
    }

    /**
     * Eliminar un movimiento por ID
     * @param accountNumber El número de cuenta
     * @param transactionId El ID del movimiento
     */
    @Override
    @Transactional
    public AccountDto delete(Long accountNumber, String transactionId) {
        var account = accountService.findByNumberAndValidateExists(accountNumber);
        var transaction = findByIdAndValidateExists(transactionId);
        account.reverseBalance(transaction.getValue());
        this.transactionRepository.delete(transaction);
        return new AccountDto(account);
    }
}
