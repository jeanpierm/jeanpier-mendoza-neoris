package com.jmendoza.customer.dto;

import com.jmendoza.customer.core.annotations.EnumPattern;
import com.jmendoza.customer.core.constraints.Post;
import com.jmendoza.customer.domain.Customer;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDto {
    @NotEmpty(groups = {Post.class}, message = "La identificación es requerida")
    @Size(min = 10, max = Customer.ID_LENGTH, message = "La identificación debe tener 10 a 13 caracteres")
    private String identification;

    private String state;

    @NotEmpty(groups = {Post.class}, message = "El nombre es requerido")
    @Size(max = 255)
    private String name;

    @NotEmpty(groups = {Post.class}, message = "El género es requerido")
    @EnumPattern(regexp = "M|F|O")
    private String gender;

    @Min(0)
    @Max(150)
    @NotNull(groups = {Post.class}, message = "La edad es requerida")
    private Short age;

    @Size(max = 255, message = "La dirección debe tener máximo 255 caracteres")
    private String address;

    @Size(max = 20, message = "El teléfono debe tener máximo 20 caracteres")
    private String phone;

    public CustomerDto(Customer customer) {
        this.identification = customer.getPersonId();
        this.state = customer.getState() ? "Activo" : "Inactivo";
        this.name = customer.getName();
        this.gender = customer.getGender().name();
        this.age = customer.getAge();
        this.address = customer.getAddress();
        this.phone = customer.getPhone();
    }
}
