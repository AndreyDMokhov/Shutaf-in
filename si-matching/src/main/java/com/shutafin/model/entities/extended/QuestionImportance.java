package com.shutafin.model.entities.extended;

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
@Table(name = "I_QUESTION_IMPORTANCE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionImportance extends AbstractKeyConstEntity {

    @Column(name = "WEIGHT", nullable = false)
    private Integer weight;

}
