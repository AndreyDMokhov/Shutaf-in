package com.shutafin.model.entities.matching;

import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Question;

/**
 * Created by evgeny on 8/20/2017.
 */
public class QuestionAnswer {

    private Question question;
    private Answer answer;

    public QuestionAnswer() {
    }

    public QuestionAnswer(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
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

    @Override
    public int hashCode() {
        return 1;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return question.equals(((QuestionAnswer)obj).getQuestion()) && answer.equals(((QuestionAnswer)obj).getAnswer());
    }

}
