package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USER_INFO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfo extends AbstractBaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @JoinColumn(name = "CURRENT_CITY_ID")
    @OneToOne
    private City currentCity;

    @JoinColumn(name = "GENDER_ID")
    @OneToOne
    private Gender gender;

    @Column(name = "DAY_OF_BIRTH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dayOfBirth;

    @Column(name = "FACEBOOK_LINK")
    private String facebookLink;

    @Column(name = "PROFESSION")
    private String profession;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

}
