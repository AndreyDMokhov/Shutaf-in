package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "USER_CREDENTIALS")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
