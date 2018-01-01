package com.shutafin.model.base;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AbstractLocalizedConstEntity extends AbstractKeyConstEntity {

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "LANGUAGE_ID", nullable = false)
    private Integer languageId;

}
