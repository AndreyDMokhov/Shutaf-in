package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "USER_SESSION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Cacheable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSession extends AbstractEntity {
    @Column(name = "USER_ID", nullable = false, unique = true)
    private Long userId;

    @Column(name = "IS_VALID", nullable = false)
    private Boolean isValid;

    @Column(name = "SESSION_ID", nullable = false, unique = true)
    private String sessionId;

    @Column(name = "IS_EXPIRABLE", nullable = false)
    private Boolean isExpirable;

    @Column(name = "EXPIRATION_TIME", nullable = false, updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

}
