package com.jmendoza.account.dto;

import com.jmendoza.account.core.annotations.EnumPattern;
import com.jmendoza.account.domain.Account;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountDto {
    @EnumPattern(regexp = "AHO|CTE")
    @NotEmpty(message = "El tipo de cuenta es requerido")
    private String type;

    @DecimalMin(value = "1", message = "El saldo inicial debe ser mayor a 0")
    @NotNull(message = "El saldo inicial es requerido")
    private BigDecimal balance;

    @Size(min = 10, max = Account.CUSTOMER_ID_LENGTH, message = "Identificación del cliente no cumple longitud requerida")
    @NotEmpty(message = "La identificación del cliente es requerida")
    private String customerId;
}
