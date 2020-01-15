package com.candidate.task.data.models;

import javax.persistence.*;
import javax.validation.constraints.Digits;

@MappedSuperclass
public abstract class BaseEntity {
    private Long id;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition="bigint(10)")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
