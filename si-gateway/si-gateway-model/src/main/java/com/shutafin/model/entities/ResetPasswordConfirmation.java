package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Deprecated
@Entity
@Table(name = "RESET_PASSWORD_CONFIRMATION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResetPasswordConfirmation extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @Column(name = "URL_LINK", nullable = false, unique = true)
    private String urlLink;

    @Column(name = "IS_CONFIRMED", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "EXPIRES_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

}
