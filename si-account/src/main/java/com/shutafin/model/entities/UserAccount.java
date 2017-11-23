package com.shutafin.model.entities;

import com.shutafin.model.base.AbstractEntity;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.types.AccountStatus;
import com.shutafin.model.types.AccountStatusConverter;
import com.shutafin.model.types.AccountType;
import com.shutafin.model.types.AccountTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ACCOUNT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAccount extends AbstractEntity {

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
