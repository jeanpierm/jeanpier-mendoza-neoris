package com.jmendoza.customer.domain;

import com.jmendoza.customer.core.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad Persona
 */
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString(callSuper = true)
public class Person extends BaseEntity {

    public static final int ID_LENGTH = 13;

    /**
     * Identificación de la persona
     */
    @Id
    @Column(name = "person_id", nullable = false, length = ID_LENGTH)
    private String personId;

    /**
     * Nombre de la persona
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Género de la persona
     */
    @Column(name = "gender", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Edad de la persona
     */
    @Column(name = "age")
    private Short age;

    /**
     * Dirección de la persona
     */
    @Column(name = "address")
    private String address;

    /**
     * Teléfono de la persona
     */
    @Column(name = "phone", length = 20)
    private String phone;
}
