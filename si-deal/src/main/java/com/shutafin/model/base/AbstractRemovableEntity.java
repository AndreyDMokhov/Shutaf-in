package com.shutafin.model.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AbstractRemovableEntity extends AbstractModifiableEntity {

    @Column(name = "IS_DELETED", nullable = false)
    private Boolean isDeleted;

}
