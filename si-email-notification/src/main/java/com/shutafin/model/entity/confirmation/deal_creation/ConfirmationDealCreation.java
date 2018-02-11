package com.shutafin.model.entity.confirmation.deal_creation;

import com.shutafin.model.entity.BaseConfirmation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CONFIRMATION_DEAL_CREATION")
@NoArgsConstructor
@Data
public class ConfirmationDealCreation extends BaseConfirmation {

    @Builder
    public ConfirmationDealCreation(String confirmationUUID, Boolean isConfirmed, Date expiresAt, Long dealId) {
        super(confirmationUUID, isConfirmed, expiresAt);
        this.dealId = dealId;
    }

    @Column(name = "DEAL_ID", nullable = false)
    private Long dealId;

}
