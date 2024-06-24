package com.jmendoza.customer.dto;

import com.jmendoza.customer.core.annotations.EnumPattern;
import com.jmendoza.customer.core.constraints.Put;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateCustomerDto {
    @NotEmpty(groups = {Put.class}, message = "El nombre es requerido")
    @Size(max = 255)
    private String name;

    @NotEmpty(groups = {Put.class}, message = "El género es requerido")
    @EnumPattern(regexp = "M|F|O")
    private String gender;

    @Min(0)
    @Max(150)
    @NotNull(groups = {Put.class}, message = "La edad es requerida")
    private Short age;

    @Size(max = 255, message = "La dirección debe tener máximo 255 caracteres")
    private String address;

    @Size(max = 20, message = "El teléfono debe tener máximo 20 caracteres")
    private String phone;
}
