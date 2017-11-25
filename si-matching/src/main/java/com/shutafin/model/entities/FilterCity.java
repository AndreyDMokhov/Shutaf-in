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
 * Created by evgeny on 9/13/2017.
 */
@Entity
@Table(name = "FILTER_CITY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilterCity extends AbstractBaseEntity {

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "CITY_ID", nullable = false)
    private Integer cityId;

}
