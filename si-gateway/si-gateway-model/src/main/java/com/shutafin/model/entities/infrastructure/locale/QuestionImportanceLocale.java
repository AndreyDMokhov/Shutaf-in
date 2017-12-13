package com.shutafin.model.entities.infrastructure.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.infrastructure.QuestionImportance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Deprecated
@Entity
@Table(name = "I_QUESTION_IMPORTANCE_LOCALE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionImportanceLocale extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "QUESTION_IMPORTANCE_ID", nullable = false)
    @ManyToOne
    private QuestionImportance questionImportance;

}
