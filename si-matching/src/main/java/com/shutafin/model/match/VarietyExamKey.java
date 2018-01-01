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
@Table(name = "VARIETY_EXAM_KEY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VarietyExamKey extends AbstractBaseEntity {

    @Column(name = "EXAM_KEY", nullable = false)
    private String userExamKey;

}
