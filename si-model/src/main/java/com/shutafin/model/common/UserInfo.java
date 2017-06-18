package com.shutafin.model.common;


import com.shutafin.model.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "USER_INFO")
public class UserInfo extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @OneToOne
    private User user;

    @JoinColumn(name = "GENDER_ID")
    @ManyToOne
    private Gender gender;

    @Column(name = "AGE")
    private Integer age;

    public UserInfo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
