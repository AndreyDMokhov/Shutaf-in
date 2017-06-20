package com.shutafin.model.infrastructure;

import com.shutafin.model.AbstractConstEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by evgeny on 6/20/2017.
 */
@Entity
@Table(name = "I_LANGUAGE")
public class Language extends AbstractConstEntity {
    @Column(name = "LANGUAGE_NATIVE_NAME", nullable = false, length = 100)
    private String languageNativeName;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = true;

    public Language() {
    }

    public String getLanguageNativeName() {
        return languageNativeName;
    }

    public void setLanguageNativeName(String languageNativeName) {
        this.languageNativeName = languageNativeName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
