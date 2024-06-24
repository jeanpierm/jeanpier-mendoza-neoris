package com.jmendoza.account.controller;

import com.jmendoza.account.dto.*;
import com.jmendoza.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "cuentas", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Buscar cuentas", description = "Buscar todas las cuentas")
    @GetMapping
    public ApiResponse<FindAccountsDto> find() {
        var accounts = accountService.find();
        return ApiResponse.ok(new FindAccountsDto(accounts));
    }

    @Operation(summary = "Buscar cuenta", description = "Buscar una cuenta por ID")
    @GetMapping(path = "{id}")
    public ApiResponse<AccountDto> find(@PathVariable Long id) {
        return ApiResponse.ok(accountService.find(id));
    }

    @Operation(summary = "Crear cuenta", description = "Crear una cuenta")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AccountDto>> create(@Validated @RequestBody CreateAccountDto dto) {
        var account = accountService.create(dto);
        var location = URI.create("/cuentas/" + account.getNumber());
        return ResponseEntity.created(location).body(ApiResponse.ok(account));
    }

    @Operation(summary = "Actualizar cuenta", description = "Actualizar una cuenta")
    @PatchMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AccountDto>> update(@PathVariable Long id,
                                                          @Validated @RequestBody UpdateAccountDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(accountService.update(id, dto)));
    }

    @Operation(summary = "Eliminar cuenta", description = "Eliminar una cuenta")
    @PatchMapping(path = "{id}/desactiva")
    public ApiResponse<Void> deactivate(@PathVariable Long id) {
        accountService.deactivate(id);
        return ApiResponse.ok();
    }

    @Operation(summary = "Activar cuenta", description = "Activar una cuenta")
    @PatchMapping(path = "{id}/activa")
    public ApiResponse<Void> activate(@PathVariable Long id) {
        accountService.activate(id);
        return ApiResponse.ok();
    }

    @Operation(summary = "Eliminar cuenta", description = "Eliminar una cuenta")
    @DeleteMapping(path = "{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ApiResponse.ok();
    }
}
