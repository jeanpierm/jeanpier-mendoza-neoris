package com.jmendoza.customer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at", updatable = false, nullable = false)
    @JsonIgnore
    protected Instant createdAt = Instant.now();

    @UpdateTimestamp
    @Column(name = "modified_at")
    @JsonIgnore
    protected Instant modifiedAt;
}