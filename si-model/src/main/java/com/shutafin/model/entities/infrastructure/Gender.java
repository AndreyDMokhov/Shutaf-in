package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractKeyConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "I_GENDER")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class Gender extends AbstractKeyConstEntity {

    public Gender() {
    }

    @Override
    public int hashCode() {
        return getId();
    }

}
