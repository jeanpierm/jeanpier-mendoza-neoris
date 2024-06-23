package com.jmendoza.customer.service;

import com.jmendoza.customer.dto.CreateCustomerDto;
import com.jmendoza.customer.dto.CustomerDto;
import com.jmendoza.customer.dto.UpdateCustomerDto;
import com.jmendoza.customer.dto.UpdatePasswordDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> find();

    CustomerDto find(String id);

    CustomerDto create(CreateCustomerDto customerDto);

    CustomerDto update(String id, UpdateCustomerDto customerDto);

    CustomerDto partialUpdate(String id, UpdateCustomerDto customerDto);

    void updatePassword(String id, UpdatePasswordDto customerDto);

    void delete(String id);

    void deactivate(String id);

    void activate(String id);
}
