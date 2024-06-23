package com.jmendoza.account.controller;

import com.jmendoza.account.dto.*;
import com.jmendoza.account.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Buscar movimientos", description = "Buscar todos los movimientos")
    @GetMapping(path = "movimientos")
    public ApiResponse<FindTransactionsDto> find() {
        var transactions = transactionService.find();
        return ApiResponse.ok(new FindTransactionsDto(transactions));
    }

    @Operation(summary = "Buscar movimiento", description = "Buscar un movimiento por ID")
    @GetMapping(path = "movimientos/{id}")
    public ApiResponse<TransactionDto> find(@PathVariable String id) {
        return ApiResponse.ok(transactionService.find(id));
    }

    @Operation(summary = "Crear movimiento", description = "Crear un movimiento")
    @PostMapping(path = "cuentas/{accountNumber}/movimientos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<TransactionDto>> create(@PathVariable Long accountNumber,
                                                              @Validated @RequestBody CreateTransactionDto dto) {
        var transaction = transactionService.create(accountNumber, dto);
        var location = URI.create("/movimientos/" + transaction.getTransactionId());
        return ResponseEntity.created(location).body(ApiResponse.ok(transaction));
    }

    @Operation(summary = "Actualizar movimiento", description = "Actualizar un movimiento")
    @PatchMapping(path = "cuentas/{accountNumber}/movimientos/{transactionId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<TransactionDto>> update(@PathVariable Long accountNumber,
                                                              @PathVariable String transactionId,
                                                              @Validated @RequestBody UpdateTransactionDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(transactionService.update(accountNumber, transactionId, dto)));
    }

    @Operation(summary = "Eliminar movimiento", description = "Eliminar un movimiento")
    @DeleteMapping(path = "cuentas/{accountNumber}/movimientos/{transactionId}")
    public ApiResponse<AccountDto> delete(@PathVariable Long accountNumber, @PathVariable String transactionId) {
        return ApiResponse.ok(transactionService.delete(accountNumber, transactionId));
    }
}
