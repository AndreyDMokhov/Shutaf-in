package com.shutafin.model.entities.matching;

import com.shutafin.model.AbstractConstEntity;
import com.shutafin.model.AbstractKeyConstEntity;
import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.AnswerExtended;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER_SIMILARITY")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class AnswerSimilarity extends AbstractKeyConstEntity {

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private AnswerExtended answer;

    @JoinColumn(name = "ANSWER_TO_COMPARE_ID", nullable = false)
    @ManyToOne
    private AnswerExtended answerToCompare;

    @Column(name = "SIMILARITY_SCORE", nullable = false)
    private Integer similarityScore;

    public AnswerSimilarity() {
    }

    public AnswerSimilarity(AnswerExtended answer, AnswerExtended answerToCompare, Integer similarityScore) {
        this.answer = answer;
        this.answerToCompare = answerToCompare;
        this.similarityScore = similarityScore;
    }

    public AnswerExtended getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerExtended answer) {
        this.answer = answer;
    }

    public AnswerExtended getAnswerToCompare() {
        return answerToCompare;
    }

    public void setAnswerToCompare(AnswerExtended answerToCompare) {
        this.answerToCompare = answerToCompare;
    }

    public Integer getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(Integer similarityScore) {
        this.similarityScore = similarityScore;
    }
}
