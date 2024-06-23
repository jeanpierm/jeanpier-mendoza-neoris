package com.jmendoza.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FindCustomersDto {
    private List<CustomerDto> customers;
}
