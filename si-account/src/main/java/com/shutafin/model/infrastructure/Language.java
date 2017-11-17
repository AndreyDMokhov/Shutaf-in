package com.shutafin.model.infrastructure;

import com.shutafin.model.base.AbstractConstEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "I_LANGUAGE")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Language extends AbstractConstEntity {
    @Column(name = "LANGUAGE_NATIVE_NAME", nullable = false, length = 100)
    private String languageNativeName;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = true;

}
