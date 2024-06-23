package com.jmendoza.customer.controller;

import com.jmendoza.customer.core.constraints.Post;
import com.jmendoza.customer.core.constraints.Put;
import com.jmendoza.customer.dto.*;
import com.jmendoza.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "clientes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Buscar clientes", description = "Buscar todos los clientes")
    @GetMapping
    public ApiResponse<FindCustomersDto> find() {
        var customers = customerService.find();
        return ApiResponse.ok(new FindCustomersDto(customers));
    }

    @Operation(summary = "Buscar cliente", description = "Buscar un cliente por ID")
    @GetMapping(path = "{id}")
    public ApiResponse<CustomerDto> find(@PathVariable String id) {
        return ApiResponse.ok(customerService.find(id));
    }

    @Operation(summary = "Crear cliente", description = "Crear un cliente")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<CustomerDto>> create(@Validated(Post.class) @RequestBody CreateCustomerDto dto) {
        var customerCreated = customerService.create(dto);
        var location = URI.create("/clientes/" + customerCreated.getIdentification());
        return ResponseEntity.created(location).body(ApiResponse.ok(customerCreated));
    }

    @Operation(summary = "Actualizar cliente", description = "Actualizar un cliente")
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<CustomerDto> update(@PathVariable String id,
                                           @Validated(Put.class) @RequestBody UpdateCustomerDto dto) {
        return ApiResponse.ok(customerService.update(id, dto));
    }

    @Operation(summary = "Actualizar parcialmente cliente", description = "Actualizar parcialmente un cliente")
    @PatchMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<CustomerDto> partialUpdate(@PathVariable String id,
                                                  @Validated @RequestBody UpdateCustomerDto dto) {
        return ApiResponse.ok(customerService.partialUpdate(id, dto));
    }

    @Operation(summary = "Actualizar contraseña", description = "Actualizar la contraseña de un cliente")
    @PatchMapping(path = "{id}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Void> updatePassword(@PathVariable String id,
                                            @Validated @RequestBody UpdatePasswordDto dto) {
        customerService.updatePassword(id, dto);
        return ApiResponse.ok();
    }


    @Operation(summary = "Desactivar cliente", description = "Desactivar un cliente")
    @PatchMapping(path = "{id}/desactiva")
    public ApiResponse<Void> deactivate(@PathVariable String id) {
        customerService.deactivate(id);
        return ApiResponse.ok();
    }

    @Operation(summary = "Activar cliente", description = "Activar un cliente")
    @PatchMapping(path = "{id}/activa")
    public ApiResponse<Void> activate(@PathVariable String id) {
        customerService.activate(id);
        return ApiResponse.ok();
    }

    @Operation(summary = "Eliminar cliente", description = "Eliminar un cliente")
    @DeleteMapping(path = "{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        customerService.delete(id);
        return ApiResponse.ok();
    }

}
