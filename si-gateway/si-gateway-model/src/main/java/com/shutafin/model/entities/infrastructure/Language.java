package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractConstEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by evgeny on 6/20/2017.
 */
@Entity
@Table(name = "I_LANGUAGE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Deprecated
public class Language extends AbstractConstEntity {
    @Column(name = "LANGUAGE_NATIVE_NAME", nullable = false, length = 100)
    private String languageNativeName;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = true;

}
