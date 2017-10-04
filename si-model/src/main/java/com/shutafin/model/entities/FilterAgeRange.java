package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by evgeny on 10/1/2017.
 */
@Entity
@Table(name = "FILTER_AGE_RANGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilterAgeRange extends AbstractBaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @Column(name = "FROM_AGE", nullable = false)
    private Integer fromAge;

    @Column(name = "TO_AGE", nullable = false)
    private Integer toAge;

}
