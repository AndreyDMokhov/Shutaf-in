package com.shutafin.model.entities.extended;

import com.shutafin.model.base.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "USER_QUESTION_EXTENDED_ANSWER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQuestionExtendedAnswer extends AbstractBaseEntity {

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

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
