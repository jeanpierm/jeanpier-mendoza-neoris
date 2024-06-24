package com.jmendoza.account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateTransactionDto {
    @NotNull(message = "El valor de la transacción es requerido")
    private BigDecimal value;
}
