package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractKeyConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "I_CITY")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class City extends AbstractKeyConstEntity {

    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    @ManyToOne
    private Country country;

    public City() {
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        return getId();
    }

}
