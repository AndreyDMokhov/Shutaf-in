package com.shutafin.model.base;

import lombok.*;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class AbstractConstEntity extends AbstractKeyConstEntity {

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
}
