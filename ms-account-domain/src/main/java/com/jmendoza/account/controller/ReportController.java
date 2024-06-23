package com.jmendoza.account.controller;

import com.jmendoza.account.dto.AccountsReportDto;
import com.jmendoza.account.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Reporte estado de cuentas", description = "Generar reporte del estado de cuentas de un cliente")
    @GetMapping(path = "/clientes/{customerId}/cuentas/reportes")
    public ResponseEntity<AccountsReportDto> accountsReport(
            @PathVariable String customerId,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.generateAccountsReport(customerId, startDate, endDate));
    }
}
