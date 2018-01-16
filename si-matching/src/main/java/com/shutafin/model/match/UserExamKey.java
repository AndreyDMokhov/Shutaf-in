package com.shutafin.model.match;

import com.shutafin.model.base.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_EXAM_KEY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserExamKey extends AbstractBaseEntity {

    @Column( name= "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "EXAM_KEY", nullable = false)
    private String examKey;

    @Column(name = "EXAM_KEY_REGEXP", nullable = false)
    private String examKeyRegExp;

    @Column(name = "IS_MATCHING_ENABLED", nullable = false)
    private Boolean isMatchingEnabled = true;

}
