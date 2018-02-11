package com.shutafin.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
public class BaseUserConfirmation extends BaseConfirmation {

    public BaseUserConfirmation(Long userId, String confirmationUUID, Boolean isConfirmed, Date expiresAt) {
        super(confirmationUUID, isConfirmed, expiresAt);
        this.userId = userId;
    }

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

}
