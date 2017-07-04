package com.shutafin.model;


import com.shutafin.model.entities.infrastructure.Language;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public abstract class AbstractLocalizedConstEntity extends AbstractConstEntity{

    @JoinColumn(name = "LANGUAGE_ID", nullable = false)
    @ManyToOne
    private Language language;

    public AbstractLocalizedConstEntity() {
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
