package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractKeyConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "I_QUESTION_IMPORTANCE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class QuestionImportance extends AbstractKeyConstEntity {

    @Column(name = "WEIGHT", nullable = false)
    private Integer weight;

    public QuestionImportance() {
    }

    public QuestionImportance(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
