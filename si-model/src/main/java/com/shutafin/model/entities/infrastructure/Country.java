package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractKeyConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "I_COUNTRY")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class Country extends AbstractKeyConstEntity {

    public Country() {
    }

}
