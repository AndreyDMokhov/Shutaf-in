package com.shutafin.model.entities.extended;

import com.shutafin.model.base.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MAX_USER_MATCHING_SCORE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaxUserMatchingScore extends AbstractBaseEntity {

    @Column(name = "USER_ID", nullable = false, unique = true)
    private Long userId;

    @Column(name = "SCORE", nullable = false)
    private Integer score;

}
