package com.shutafin.model.entities.infrastructure.locale;

import com.shutafin.model.AbstractLocalizedConstEntity;
import com.shutafin.model.entities.infrastructure.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Deprecated
@Entity
@Table(name = "I_ANSWER_LOCALE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@Getter
@Setter
public class AnswerLocale extends AbstractLocalizedConstEntity {

    @JoinColumn(name = "ANSWER_ID", nullable = false)
    @ManyToOne
    private Answer answer;

}
