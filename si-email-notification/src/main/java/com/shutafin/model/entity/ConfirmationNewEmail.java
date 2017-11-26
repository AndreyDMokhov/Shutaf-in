package com.shutafin.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CONFIRMATION_NEW_EMAIL")
@NoArgsConstructor
@Data
public class ConfirmationNewEmail extends BaseConfirmation {

    @Builder()
    public ConfirmationNewEmail(Long userId, String confirmationUUID, Boolean isConfirmed, Date expiresAt,
                                String newEmail, ConfirmationNewEmail connectedConfirmationNewEmail) {
        super(userId, confirmationUUID, isConfirmed, expiresAt);
        this.newEmail = newEmail;
        this.connectedConfirmationNewEmail = connectedConfirmationNewEmail;
    }

    @Column(name = "NEW_EMAIL")
    private String newEmail;

    @JoinColumn(name = "CONNECTED_CONFIRMATION")
    @OneToOne
    private ConfirmationNewEmail connectedConfirmationNewEmail;

}
