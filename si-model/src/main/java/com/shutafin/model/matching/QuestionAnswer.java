package com.shutafin.model.matching;

import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Question;
import lombok.*;

/**
 * Created by evgeny on 8/20/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionAnswer {

    private Question question;
    private Answer answer;


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
