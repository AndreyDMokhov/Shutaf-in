package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.model.entities.infrastructure.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by evgeny on 9/23/2017.
 */
@Entity
@Table(name = "USER_QUESTION_ANSWER_GENDER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQuestionAnswerGender extends AbstractBaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private Question question;

    @JoinColumn(name = "GENDER_ID", nullable = false)
    @ManyToOne
    private Gender gender;

}
