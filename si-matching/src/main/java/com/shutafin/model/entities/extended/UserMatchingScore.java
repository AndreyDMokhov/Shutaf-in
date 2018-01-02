package com.shutafin.model.entities.extended;

import com.shutafin.model.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_MATCHING_SCORE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Cacheable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMatchingScore extends AbstractEntity {

    @Column(name = "USER_ORIGIN_ID", nullable = false)
    private Long userOriginId;

    @Column(name = "USER_TO_MATCH_ID", nullable = false)
    private Long userToMatchId;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

}
