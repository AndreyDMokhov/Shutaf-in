package com.shutafin.model.base;


import com.shutafin.model.infrastructure.Language;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AbstractLocalizedConstEntity extends AbstractConstEntity {

    @JoinColumn(name = "LANGUAGE_ID", nullable = false)
    @ManyToOne
    private Language language;

}
