package com.shutafin.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractConstEntity {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public AbstractConstEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
