package com.shutafin.model.entities.infrastructure;

import com.shutafin.model.AbstractKeyConstEntity;
import com.shutafin.model.entities.types.QuestionType;
import com.shutafin.model.entities.types.QuestionTypeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by evgeny on 8/10/2017.
 */
@Entity
@Table(name = "I_QUESTION")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@Getter
@Setter
public class Question extends AbstractKeyConstEntity implements Comparable<Question> {

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = true;

    @Column(name = "QUESTION_TYPE", nullable = false)
    @Convert(converter = QuestionTypeConverter.class)
    private QuestionType questionType;

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        return getId().equals( ((Question)obj).getId() );
    }

    @Override
    public int compareTo(Question o) {
        return getId() - o.getId();
    }
}
