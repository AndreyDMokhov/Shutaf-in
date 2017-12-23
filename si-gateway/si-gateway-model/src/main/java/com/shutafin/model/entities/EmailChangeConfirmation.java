package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EMAIL_CHANGE_CONFIRMATION")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Deprecated
public class EmailChangeConfirmation extends AbstractEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @Column(name = "IS_NEW_EMAIL", nullable = false)
    private Boolean isNewEmail;

    @Column(name = "UPDATE_EMAIL_ADDRESS", nullable = true)
    private String updateEmailAddress;

    @Column(name = "URL_LINK", nullable = false, unique = true)
    private String urlLink;

    @Column(name = "IS_CONFIRMED", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "EXPIRES_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    @JoinColumn(name = "CONNECTED_ID", nullable = true)
    @OneToOne
    private EmailChangeConfirmation connectedId;

}
