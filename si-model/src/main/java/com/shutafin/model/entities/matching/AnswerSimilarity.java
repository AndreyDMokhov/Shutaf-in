package com.shutafin.model.entities.matching;

import com.shutafin.model.AbstractConstEntity;
import com.shutafin.model.entities.infrastructure.Answer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER_SIMILARITY")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class AnswerSimilarity extends AbstractConstEntity {

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private Answer answer;

    @JoinColumn(name = "ANSWER_TO_COMPARE_ID", nullable = false)
    @ManyToOne
    private Answer answerToCompare;

    @Column(name = "SIMILARITY_SCORE", nullable = false)
    private Integer similarityScore;

    public AnswerSimilarity() {
    }

    public AnswerSimilarity(Answer answer, Answer answerToCompare, Integer similarityScore) {
        this.answer = answer;
        this.answerToCompare = answerToCompare;
        this.similarityScore = similarityScore;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Answer getAnswerToCompare() {
        return answerToCompare;
    }

    public void setAnswerToCompare(Answer answerToCompare) {
        this.answerToCompare = answerToCompare;
    }

    public Integer getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(Integer similarityScore) {
        this.similarityScore = similarityScore;
    }
}
