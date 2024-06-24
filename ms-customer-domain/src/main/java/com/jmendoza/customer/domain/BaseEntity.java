package com.jmendoza.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Clase base para las entidades
 */
@MappedSuperclass
public class BaseEntity {
    /**
     * La fecha y hora en la que se creó la entidad
     */
    @Column(name = "created_at", updatable = false, nullable = false)
    @JsonIgnore
    protected Instant createdAt = Instant.now();


    /**
     * La fecha y hora en la que se modificó la entidad por última vez
     */
    @UpdateTimestamp
    @Column(name = "modified_at")
    @JsonIgnore
    protected Instant modifiedAt;
}