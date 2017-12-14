package com.shutafin.model.filter;

import com.shutafin.model.base.AbstractBaseEntity;
import com.shutafin.model.entities.User;
import com.shutafin.model.infrastructure.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @JoinColumn(name = "USER_ID", nullable = false)
    @OneToOne
    private User user;

    @JoinColumn(name = "GENDER_ID", nullable = false)
    @ManyToOne
    private Gender gender;


}
