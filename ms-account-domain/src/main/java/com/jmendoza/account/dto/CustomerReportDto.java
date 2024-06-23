package com.jmendoza.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReportDto {
    private String identification;
    private String name;

    public CustomerReportDto(CustomerDto customer) {
        this.identification = customer.getIdentification();
        this.name = customer.getName();
    }
}
