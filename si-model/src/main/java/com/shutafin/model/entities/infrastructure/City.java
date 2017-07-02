package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "I_CITY")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class City extends AbstractConstEntity{

    @JoinColumn(name = "LANGUAGE_ID", nullable = false)
    @ManyToOne
    private Language language;

    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    @ManyToOne
    private Country country;

    public City() {
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
