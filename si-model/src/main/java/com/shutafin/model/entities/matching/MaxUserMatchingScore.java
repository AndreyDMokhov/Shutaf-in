package com.shutafin.model.entities.matching;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "MAX_USER_MATCHING_SCORE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaxUserMatchingScore extends AbstractBaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

}
