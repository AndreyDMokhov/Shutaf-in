package com.shutafin.model.entities.extended;

import com.shutafin.model.AbstractKeyConstEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER_SIMILARITY")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerSimilarity extends AbstractKeyConstEntity {

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private AnswerExtended answer;

    @JoinColumn(name = "ANSWER_TO_COMPARE_ID", nullable = false)
    @ManyToOne
    private AnswerExtended answerToCompare;

    @Column(name = "SIMILARITY_SCORE", nullable = false)
    private Integer similarityScore;

}
