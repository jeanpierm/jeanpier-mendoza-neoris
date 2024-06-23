package com.jmendoza.account.service;

import com.jmendoza.account.dto.AccountsReportDto;

import java.time.LocalDate;

public interface ReportService {
    AccountsReportDto generateAccountsReport(String customerId, LocalDate startDate, LocalDate endDate);
}
