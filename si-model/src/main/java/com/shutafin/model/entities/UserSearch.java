package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "")
public class UserSearch extends AbstractEntity{

    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public UserSearch setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    private String lastName;

        public UserSearch() {
        }




}
