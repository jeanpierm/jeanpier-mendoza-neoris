package com.jmendoza.account.dto;

import com.jmendoza.account.core.annotations.EnumPattern;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateAccountDto {
    @EnumPattern(regexp = "AHORROS|CORRIENTE")
    private String type;

    @DecimalMin(value = "1", message = "El saldo inicial debe ser mayor a 0")
    private BigDecimal balance;
}
