package com.jmendoza.account.service;

import com.jmendoza.account.dto.AccountReportDto;
import com.jmendoza.account.dto.AccountsReportDto;
import com.jmendoza.account.repository.AccountRepository;
import com.jmendoza.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CustomerMessageProducer customerMessageProducer;

    /**
     * Genera un reporte de estado de cuentas
     *
     * @param customerId El ID del cliente
     * @param startDate  La fecha de inicio
     * @param endDate
     * @return
     */
    @Override
    public AccountsReportDto generateAccountsReport(String customerId, LocalDate startDate, LocalDate endDate) {
        Instant startDateInstant = startDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant endDateInstant = endDate.atTime(23, 59, 59).toInstant(ZoneOffset.UTC); // end of the day;
        log.info("Generando reporte de estado de cuentas para el cliente {} desde {} hasta {}", customerId, startDateInstant, endDateInstant);

        var accounts = accountRepository.findByCustomerId(customerId);
        var accountsReport = accounts.stream()
                .parallel()
                .map(account -> {
                    var transactions = transactionRepository
                            .findByAccountNumberAndCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(
                                    account.getNumber(),
                                    endDateInstant,
                                    startDateInstant);
                    return new AccountReportDto(account, transactions);
                })
                .toList();
        var customer = customerMessageProducer.findCustomer(customerId);
        var report = new AccountsReportDto(customer, accountsReport);
        log.info("Reporte de estado de cuentas generado con exito");
        return report;
    }
}
