package com.shutafin.model.entities.matching;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.entities.infrastructure.QuestionImportance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Deprecated
@Entity
@Table(name = "USER_EXTENDED_QUESTION_ANSWER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQuestionExtendedAnswer extends AbstractBaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private QuestionExtended question;

    @JoinColumn(name = "ANSWER_ID")
    @ManyToOne
    private AnswerExtended answer;

    @JoinColumn(name = "QUESTION_IMPORTANCE_ID", nullable = false)
    @ManyToOne
    private QuestionImportance importance;

}
