package com.shutafin.model.entity.confirmation.registration;

import com.shutafin.model.entity.BaseUserConfirmation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CONFIRMATION_REGISTRATION")
@NoArgsConstructor
@Data
public class ConfirmationRegistration extends BaseUserConfirmation {

    @Builder
    public ConfirmationRegistration(Long userId, String confirmationUUID, Boolean isConfirmed, Date expiresAt) {
        super(userId, confirmationUUID, isConfirmed, expiresAt);
    }
}
