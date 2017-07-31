package com.shutafin.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractConstEntity extends AbstractKeyConstEntity {

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public AbstractConstEntity() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
