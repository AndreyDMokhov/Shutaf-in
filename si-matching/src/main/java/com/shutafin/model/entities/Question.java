package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractKeyConstEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "I_QUESTION")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question extends AbstractKeyConstEntity implements Comparable<Question> {

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = true;


    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        return getId().equals( ((Question)obj).getId() );
    }

    @Override
    public int compareTo(Question o) {
        return getId() - o.getId();
    }
}
