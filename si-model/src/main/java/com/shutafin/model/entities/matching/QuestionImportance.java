package com.shutafin.model.entities.matching;

import com.shutafin.model.AbstractConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "QUESTION_IMPORTANCE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class QuestionImportance extends AbstractConstEntity {

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
