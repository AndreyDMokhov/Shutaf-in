package com.shutafin.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractKeyConstEntity {
    @Id
    @Column(name = "ID")
    private Integer id;

    public AbstractKeyConstEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
