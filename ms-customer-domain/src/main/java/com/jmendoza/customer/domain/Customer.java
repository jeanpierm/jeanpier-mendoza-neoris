package com.jmendoza.customer.domain;

import com.jmendoza.customer.core.enums.Gender;
import com.jmendoza.customer.dto.CreateCustomerDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.yaml.snakeyaml.util.EnumUtils;

@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "customer_id", referencedColumnName = "person_id")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Customer extends Person {

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "state", nullable = false)
    private Boolean state;

    public Customer(CreateCustomerDto createCustomerDto, PasswordEncoder passwordEncoder) {
        this.setPersonId(createCustomerDto.getIdentification());
        this.setName(createCustomerDto.getName());
        this.setGender(EnumUtils.findEnumInsensitiveCase(Gender.class, createCustomerDto.getGender()));
        this.setAge(createCustomerDto.getAge());
        this.setAddress(createCustomerDto.getAddress());
        this.setPhone(createCustomerDto.getPhone());
        this.setPassword(passwordEncoder.encode(createCustomerDto.getPassword()));
        this.setState(Boolean.TRUE);
    }

    public void deactivate() {
        this.state = Boolean.FALSE;
    }

    public void activate() {
        this.state = Boolean.FALSE;
    }
}
