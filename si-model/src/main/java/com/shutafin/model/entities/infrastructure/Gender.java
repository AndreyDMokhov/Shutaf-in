package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "I_GENDER")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class Gender extends AbstractConstEntity {

    public Gender() {
    }

}
