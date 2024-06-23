package com.jmendoza.customer.domain;

import com.jmendoza.customer.core.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString(callSuper = true)
public class Person extends BaseEntity {

    public static final int ID_LENGTH = 13;

    @Id
    @Column(name = "person_id", nullable = false, length = ID_LENGTH)
    private String personId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "age")
    private Short age;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;
}
