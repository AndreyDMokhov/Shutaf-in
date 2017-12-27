package com.shutafin.model.entity.email_change;

import com.shutafin.model.entity.BaseConfirmation;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CONFIRMATION_EMAIL_CHANGE")
@NoArgsConstructor
@Data
public class ConfirmationEmailChange extends BaseConfirmation {

    @Builder()
    public ConfirmationEmailChange(Long userId, String confirmationUUID, Boolean isConfirmed, Date expiresAt,
                                   String emailChange, ConfirmationEmailChange connectedConfirmationEmailChange) {
        super(userId, confirmationUUID, isConfirmed, expiresAt);
        this.emailChange = emailChange;
        this.connectedConfirmationEmailChange = connectedConfirmationEmailChange;
    }

    @Column(name = "EMAIL_CHANGE")
    private String emailChange;

    @JoinColumn(name = "CONNECTED_CONFIRMATION")
    @OneToOne
    private ConfirmationEmailChange connectedConfirmationEmailChange;

}
