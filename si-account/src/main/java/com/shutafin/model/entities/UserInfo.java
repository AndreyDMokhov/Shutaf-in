package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractBaseEntity;
import com.shutafin.model.infrastructure.City;
import com.shutafin.model.infrastructure.Gender;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USER_INFO")
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @Column(name = "FACEBOOK_LINK")
    private String facebookLink;

    @Column(name = "PROFESSION")
    private String profession;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

}
