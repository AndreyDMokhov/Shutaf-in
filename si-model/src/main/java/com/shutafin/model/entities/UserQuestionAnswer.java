package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.entities.matching.QuestionImportance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by evgeny on 8/20/2017.
 */
@Entity
@Table(name = "USER_QUESTION_ANSWER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQuestionAnswer extends AbstractBaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private Question question;

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private Answer answer;

    @JoinColumn(name = "QUESTION_IMPORTANCE_ID")
    @ManyToOne
    private QuestionImportance questionImportance;

    public UserQuestionAnswer(User user, Question question, Answer answer) {
        this.user = user;
        this.question = question;
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public QuestionImportance getQuestionImportance() {
        return questionImportance;
    }

    public void setQuestionImportance(QuestionImportance questionImportance) {
        this.questionImportance = questionImportance;
    }
}
