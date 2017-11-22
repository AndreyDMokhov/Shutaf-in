package com.shutafin.model.entity;

import com.shutafin.model.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CONFIRMATION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Confirmation extends AbstractEntity {

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "CONFIRMATION_UUID", nullable = false, unique = true, length = 50)
    private String confirmationUUID;

    @Column(name = "IS_CONFIRMED", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "EXPIRES_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    @Column(name = "NEW_EMAIL")
    private String newEmail;

    @JoinColumn(name = "CONNECTED_CONFIRMATION")
    @OneToOne
    private Confirmation connectedConfirmation;

}
