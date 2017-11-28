package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by evgeny on 9/23/2017.
 */
@Entity
@Table(name = "FILTER_GENDER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilterGender extends AbstractBaseEntity {

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "GENDER_ID", nullable = false)
    private Integer genderId;

}
