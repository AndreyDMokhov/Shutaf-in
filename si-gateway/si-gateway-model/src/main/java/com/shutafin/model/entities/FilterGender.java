package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.infrastructure.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by evgeny on 9/23/2017.
 */
@Deprecated
@Entity
@Table(name = "FILTER_GENDER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilterGender extends AbstractBaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @OneToOne
    private User user;

    @JoinColumn(name = "GENDER_ID", nullable = false)
    @ManyToOne
    private Gender gender;

}
