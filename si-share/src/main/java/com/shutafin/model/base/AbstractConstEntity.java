package com.shutafin.model.base;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class AbstractConstEntity extends AbstractKeyConstEntity {

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
}
