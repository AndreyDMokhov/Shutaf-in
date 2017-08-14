package com.shutafin.model.entities.infrastructure.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Question;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by evgeny on 8/10/2017.
 */
@Entity
@Table(name = "I_ANSWER_LOCALE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
public class AnswerLocale extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private Answer answer;

    public AnswerLocale() {
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
