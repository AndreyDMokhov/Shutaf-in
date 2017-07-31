package com.shutafin.model;


import com.shutafin.model.entities.types.LanguageEnum;
import com.shutafin.model.entities.types.LanguageEnumConverter;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractLocalizedConstEntity extends AbstractConstEntity {

    @Convert(converter = LanguageEnumConverter.class)
    @Column(name = "LANGUAGE_ID", nullable = false)
    private LanguageEnum language;

    public AbstractLocalizedConstEntity() {
    }

    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }
}
