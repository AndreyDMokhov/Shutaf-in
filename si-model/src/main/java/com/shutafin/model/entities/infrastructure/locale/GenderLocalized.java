package com.shutafin.model.entities.infrastructure.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.infrastructure.Gender;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "I_GENDER_LOCALE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class GenderLocalized extends AbstractLocalizedConstEntity{

    @JoinColumn(name = "GENDER_ID", nullable = false)
    @ManyToOne
    private Gender gender;

    public GenderLocalized() {
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
