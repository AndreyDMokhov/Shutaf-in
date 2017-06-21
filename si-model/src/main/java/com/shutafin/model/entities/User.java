package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "USER")
public class User extends AbstractEntity {

    @Column(name = "FIRST_NAME", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 50)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, length = 50, unique = true)
    private String email;

    private Set<UserSession> userSessions = new HashSet<UserSession>();

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(mappedBy = "user")
    public Set<UserSession> getUserSessions() {
        return this.userSessions;
    }

    public void setUserSessions(Set<UserSession> userSessions) {
        this.userSessions = userSessions;
    }



    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
