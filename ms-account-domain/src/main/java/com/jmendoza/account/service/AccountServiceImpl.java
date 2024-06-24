package com.jmendoza.account.service;

import com.jmendoza.account.core.enums.AccountType;
import com.jmendoza.account.core.enums.ResponseDictionary;
import com.jmendoza.account.core.exception.NotFoundException;
import com.jmendoza.account.domain.Account;
import com.jmendoza.account.dto.AccountDto;
import com.jmendoza.account.dto.CreateAccountDto;
import com.jmendoza.account.dto.UpdateAccountDto;
import com.jmendoza.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerMessageProducer customerMessageProducer;

    /**
     * Encontrar todas las cuentas
     *
     * @return Una lista de cuentas
     */
    @Override
    public List<AccountDto> find() {
        return accountRepository.findAll()
                .stream()
                .map(AccountDto::new)
                .toList();
    }

    /**
     * Encontrar una cuenta por ID
     *
     * @param id El ID de la cuenta
     * @return La cuenta encontrada
     */
    @Override
    public AccountDto find(Long id) {
        var account = findByNumberAndValidateExists(id);
        return new AccountDto(account);

    }

    /**
     * Encontrar una cuenta por ID y validar que exista
     *
     * @param id El ID de la cuenta
     * @return La cuenta encontrada
     */
    public Account findByNumberAndValidateExists(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ResponseDictionary.ACCOUNT_NOT_FOUND, id.toString()));
    }

    /**
     * Crear una cuenta
     *
     * @param dto Los datos de la cuenta
     * @return La cuenta creada
     */
    @Override
    @Transactional
    public AccountDto create(CreateAccountDto dto) {
        Account account = new Account(dto);
        validateCustomerExists(dto.getCustomerId());
        account = accountRepository.save(account);
        log.info("Cuenta creada con numero {}", account.getNumber());
        return new AccountDto(account);
    }

    /**
     * Validar que el cliente exista
     *
     * @param customerId El ID del cliente
     */
    private void validateCustomerExists(String customerId) {
        customerMessageProducer.findCustomer(customerId);
    }

    /**
     * Actualizar una cuenta por ID
     *
     * @param id  El ID de la cuenta
     * @param dto Los datos de la cuenta
     * @return La cuenta actualizada
     */
    @Override
    @Transactional
    public AccountDto update(Long id, UpdateAccountDto dto) {
        Account account = findByNumberAndValidateExists(id);

        if (dto.getBalance() != null) account.setBalance(dto.getBalance());
        if (StringUtils.hasText(dto.getType()))
            account.setType(EnumUtils.findEnumInsensitiveCase(AccountType.class, dto.getType()));

        log.info("Cuenta actualizada con numero {}", account.getNumber());
        return new AccountDto(account);
    }

    /**
     * Desactivar una cuenta por ID
     *
     * @param id El ID de la cuenta
     */
    @Override
    @Transactional
    public void deactivate(Long id) {
        Account account = findByNumberAndValidateExists(id);
        account.deactivate();
        log.info("Cuenta desactivada con numero {}", account.getNumber());
    }

    /**
     * Activar una cuenta por ID
     *
     * @param id El ID de la cuenta
     */
    @Override
    @Transactional
    public void activate(Long id) {
        Account account = findByNumberAndValidateExists(id);
        account.activate();
        log.info("Cuenta activada con numero {}", account.getNumber());
    }

    /**
     * Eliminar una cuenta por ID
     *
     * @param id El ID de la cuenta
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Account account = findByNumberAndValidateExists(id);
        accountRepository.delete(account);
        log.info("Cuenta eliminada con numero {}", account.getNumber());
    }
}
