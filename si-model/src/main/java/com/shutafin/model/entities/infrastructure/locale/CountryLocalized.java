package com.shutafin.model.entities.infrastructure.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.infrastructure.Country;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "I_COUNTRY_LOCALE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class CountryLocalized extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    @ManyToOne
    private Country country;

    public CountryLocalized() {
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
