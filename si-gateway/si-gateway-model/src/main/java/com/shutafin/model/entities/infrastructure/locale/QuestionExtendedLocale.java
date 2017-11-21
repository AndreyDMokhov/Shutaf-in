package com.shutafin.model.entities.infrastructure.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
