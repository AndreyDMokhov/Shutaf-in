package com.shutafin.model.entities.matching;

import com.shutafin.model.AbstractEntity;
import com.shutafin.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "USER_MATCHING_SCORE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMatchingScore extends AbstractEntity {

    @JoinColumn(name = "USER_ORIGIN_ID", nullable = false)
    @ManyToOne
    private User userOrigin;

    @JoinColumn(name = "USER_TO_MATCH_ID", nullable = false)
    @ManyToOne
    private User userToMatch;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

}
