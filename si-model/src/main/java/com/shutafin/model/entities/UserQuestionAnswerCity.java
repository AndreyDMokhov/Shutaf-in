package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by evgeny on 9/13/2017.
 */
@Entity
@Table(name = "USER_QUESTION_ANSWER_CITY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQuestionAnswerCity extends AbstractBaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private Question question;

    @JoinColumn(name = "CITY_ID", nullable = false)
    @ManyToOne
    private City city;

}
