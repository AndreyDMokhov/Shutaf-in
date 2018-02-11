package com.shutafin.model.entity.confirmation.deal_user_adding;

import com.shutafin.model.entity.BaseConfirmation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CONFIRMATION_DEAL_USER_ADDING")
@NoArgsConstructor
@Data
public class ConfirmationDealUserAdding extends BaseConfirmation {

    @Builder
    public ConfirmationDealUserAdding(String confirmationUUID, Boolean isConfirmed, Date expiresAt, String groupUUID, Long dealId, Long userIdToAdd) {
        super(confirmationUUID, isConfirmed, expiresAt);
        this.groupUUID = groupUUID;
        this.dealId = dealId;
        this.userIdToAdd = userIdToAdd;
    }

    @Column(name = "GROUP_UUID", nullable = false, length = 50)
    private String groupUUID;

    @Column(name = "DEAL_ID", nullable = false)
    private Long dealId;

    @Column(name = "USER_ID_TO_ADD", nullable = false)
    private Long userIdToAdd;

}
