package com.shutafin.model.entities;

import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.types.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by evgeny on 6/20/2017.
 */
@Entity
@Table(name = "USER_ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserAccount extends AbstractBaseEntity {
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    @OneToOne
    private User user;

    @Column(name = "ACCOUNT_STATUS_ID", nullable = false)
    @Convert(converter = AccountStatusConverter.class)
    private AccountStatus accountStatus;

    @Column(name = "ACCOUNT_TYPE_ID", nullable = false)
    @Convert(converter = AccountTypeConverter.class)
    private AccountType accountType;

    @JoinColumn(name = "LANGUAGE_ID", nullable = false)
    @OneToOne
    private Language language;

    @JoinColumn(name = "USER_IMAGE_ID")
    @OneToOne
    private UserImage userImage;

}
