package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Gender;

import javax.persistence.*;

@Entity
@Table(name = "USER_INFO")
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

    public UserInfo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
