package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER_QUESTION_ANSWER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQuestionAnswer extends AbstractBaseEntity {

    @Column( name= "USER_ID", nullable = false)
    private Long userId;

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private Question question;

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private Answer answer;
}
