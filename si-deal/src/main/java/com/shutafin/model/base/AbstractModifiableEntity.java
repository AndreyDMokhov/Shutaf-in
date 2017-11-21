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
public class AbstractModifiableEntity extends AbstractEntity {

    @Column(name = "MODIFIED_BY")
    private Long modifiedByUser;
}
