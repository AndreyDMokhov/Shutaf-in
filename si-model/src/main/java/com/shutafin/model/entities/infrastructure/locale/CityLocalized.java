package com.shutafin.model.entities.infrastructure.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.infrastructure.City;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "I_CITY_LOCALE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class CityLocalized extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "CITY_ID", nullable = false)
    @ManyToOne
    private City city;

    public CityLocalized() {
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
