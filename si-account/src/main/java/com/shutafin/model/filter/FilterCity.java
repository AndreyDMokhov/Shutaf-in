package com.shutafin.model.filter;


import com.shutafin.model.base.AbstractBaseEntity;
import com.shutafin.model.entities.User;
import com.shutafin.model.infrastructure.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "CITY_ID", nullable = false)
    @ManyToOne
    private City city;

}
