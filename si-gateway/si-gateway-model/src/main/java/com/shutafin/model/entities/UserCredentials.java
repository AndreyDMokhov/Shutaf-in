package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by evgeny on 6/19/2017.
 */
@Deprecated
@Entity
@Table(name = "USER_CREDENTIALS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCredentials extends AbstractEntity {
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(name = "PASSWORD_HASH", nullable = false)
    @Lob
    private String passwordHash;

    @Column(name = "PASSWORD_SALT", nullable = false)
    @Lob
    private String passwordSalt;

}
