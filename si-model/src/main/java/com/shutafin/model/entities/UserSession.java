package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "USER_SESSION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Cacheable
public class UserSession extends AbstractEntity {
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "IS_VALID", nullable = false)
    private Boolean isValid;

    @Column(name = "SESSION_ID", nullable = false, unique = true)
    private String sessionId;

    @Column(name = "IS_EXPIRABLE", nullable = false)
    private Boolean isExpirable;


    @Column(name = "EXPIRATION_TIME", nullable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    public UserSession() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getExpirable() {
        return isExpirable;
    }

    public void setExpirable(Boolean expirable) {
        isExpirable = expirable;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
