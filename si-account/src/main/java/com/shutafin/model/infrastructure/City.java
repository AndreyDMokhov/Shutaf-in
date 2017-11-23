package com.shutafin.model.infrastructure;

import com.shutafin.model.base.AbstractKeyConstEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "I_CITY")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class City extends AbstractKeyConstEntity {

    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    @ManyToOne
    private Country country;

}
