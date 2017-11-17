package com.shutafin.model.infrastructure.locale;

import com.shutafin.model.base.AbstractLocalizedConstEntity;
import com.shutafin.model.infrastructure.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "I_CITY_LOCALE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityLocale extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "CITY_ID", nullable = false)
    @ManyToOne
    private City city;

}
