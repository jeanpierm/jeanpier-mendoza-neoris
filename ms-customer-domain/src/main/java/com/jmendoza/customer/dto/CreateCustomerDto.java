package com.jmendoza.customer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreateCustomerDto extends CustomerDto {

    @NotEmpty(message = "La contraseña es requerida")
    @Size(max = 255, min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;
}
