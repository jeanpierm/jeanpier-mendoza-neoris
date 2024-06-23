package com.jmendoza.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDto {
    private String identification;
    private String state;
    private String name;
    private String gender;
    private Short age;
    private String address;
    private String phone;
}
