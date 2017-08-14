package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractKeyConstEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by evgeny on 8/10/2017.
 */
@Entity
@Table(name = "I_ANSWER")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class Answer extends AbstractKeyConstEntity {

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private Question question;

    public Answer() {
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
