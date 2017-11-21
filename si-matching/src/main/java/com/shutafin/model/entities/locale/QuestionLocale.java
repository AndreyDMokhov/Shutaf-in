package com.shutafin.model.entities.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "I_QUESTION_LOCALE")
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
//@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionLocale extends AbstractLocalizedConstEntity {
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private Question question;


    @Column(name = "DESCRIPTION", nullable = false, unique = true)
    private String description;
}
