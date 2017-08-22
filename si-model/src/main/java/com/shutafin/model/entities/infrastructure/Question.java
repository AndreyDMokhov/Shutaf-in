package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractKeyConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by evgeny on 8/10/2017.
 */
@Entity
@Table(name = "I_QUESTION")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class Question extends AbstractKeyConstEntity {

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = true;

    public Question() {
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        return getId()==((Question)obj).getId();
    }
}
