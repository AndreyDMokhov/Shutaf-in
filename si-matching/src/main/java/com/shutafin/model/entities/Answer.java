package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractKeyConstEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "I_ANSWER")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@Getter
@Setter
public class Answer extends AbstractKeyConstEntity {

    @Column(name = "IS_UNIVERSAL", nullable = false)
    private Boolean isUniversal = false;

    @JoinColumn(name = "QUESTION_ID", nullable = false)
    @ManyToOne
    private Question question;

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        return isUniversal || ((Answer)obj).isUniversal ||  getId().equals( ((Answer)obj).getId() );
    }
}
