package com.jmendoza.customer.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordDto {
    @Size(max = 255, min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String password;
}
