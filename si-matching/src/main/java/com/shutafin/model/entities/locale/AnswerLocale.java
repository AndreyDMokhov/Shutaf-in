package com.shutafin.model.entities.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "I_ANSWER_LOCALE")
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
//@Cacheable
@NoArgsConstructor
@Getter
@Setter
public class AnswerLocale extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private Answer answer;

}
