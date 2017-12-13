package com.shutafin.model.entities.infrastructure.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.infrastructure.AnswerExtended;
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
@Table(name = "I_ANSWER_EXTENDED_LOCALE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerExtendedLocale extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private AnswerExtended answer;
}
