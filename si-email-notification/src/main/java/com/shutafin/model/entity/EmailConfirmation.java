package com.shutafin.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CONFIRMATION")
@NoArgsConstructor
@Data
public class EmailConfirmation extends Confirmation {

    @Builder()
    public EmailConfirmation(Long userId, String confirmationUUID, Boolean isConfirmed, Date expiresAt, String newEmail, EmailConfirmation connectedEmailConfirmation) {
        super(userId, confirmationUUID, isConfirmed, expiresAt);
        this.newEmail = newEmail;
        this.connectedEmailConfirmation = connectedEmailConfirmation;
    }

    @Column(name = "NEW_EMAIL")
    private String newEmail;

    @JoinColumn(name = "CONNECTED_CONFIRMATION")
    @OneToOne
    private EmailConfirmation connectedEmailConfirmation;

}
