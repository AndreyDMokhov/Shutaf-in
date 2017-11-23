package com.shutafin.model.infrastructure.locale;

import com.shutafin.model.base.AbstractLocalizedConstEntity;
import com.shutafin.model.infrastructure.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "I_CITY_LOCALE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityLocale extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "CITY_ID", nullable = false)
    @ManyToOne
    private City city;

}
