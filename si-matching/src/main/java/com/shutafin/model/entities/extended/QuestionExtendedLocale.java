package com.shutafin.model.entities.extended;

import com.shutafin.model.AbstractLocalizedConstEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "I_QUESTION_EXTENDED_LOCALE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionExtendedLocale extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private QuestionExtended question;

}
