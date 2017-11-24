package com.shutafin.model.entity;

import com.shutafin.model.base.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
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
}
