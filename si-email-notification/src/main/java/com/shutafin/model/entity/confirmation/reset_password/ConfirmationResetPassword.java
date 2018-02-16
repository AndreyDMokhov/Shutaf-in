package com.shutafin.model.entity.confirmation.reset_password;

import com.shutafin.model.entity.BaseUserConfirmation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CONFIRMATION_RESET_PASSWORD")
@NoArgsConstructor
@Data
public class ConfirmationResetPassword extends BaseUserConfirmation {

    @Builder
    public ConfirmationResetPassword(Long userId, String confirmationUUID, Boolean isConfirmed, Date expiresAt) {
        super(userId, confirmationUUID, isConfirmed, expiresAt);
    }
}
