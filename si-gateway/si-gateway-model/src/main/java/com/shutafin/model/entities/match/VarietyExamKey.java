package com.shutafin.model.entities.match;

import com.shutafin.model.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by evgeny on 9/9/2017.
 */
@Deprecated
@Entity
@Table(name = "VARIETY_EXAM_KEY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VarietyExamKey extends AbstractBaseEntity {

    @Column(name = "EXAM_KEY", nullable = false)
    private String userExamKey;

}
