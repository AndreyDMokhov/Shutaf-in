package com.shutafin.model.entities.extended;

import com.shutafin.model.base.AbstractKeyConstEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "I_ANSWER_EXTENDED")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerExtended extends AbstractKeyConstEntity {

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private QuestionExtended question;

}
