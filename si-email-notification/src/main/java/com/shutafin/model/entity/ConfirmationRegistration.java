package com.shutafin.model.entity;

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
public class ConfirmationRegistration extends BaseConfirmation {

    @Builder
    public ConfirmationRegistration(Long userId, String confirmationUUID, Boolean isConfirmed, Date expiresAt) {
        super(userId, confirmationUUID, isConfirmed, expiresAt);
    }
}
