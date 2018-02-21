package com.shutafin.model.entity.confirmation.deal_user_removing;

import com.shutafin.model.entity.BaseUserConfirmation;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CONFIRMATION_DEAL_USER_REMOVING")
@NoArgsConstructor
@Data
public class ConfirmationDealUserRemoving extends BaseUserConfirmation {

    @Builder
    public ConfirmationDealUserRemoving(Long userId, String confirmationUUID, Boolean isConfirmed, Date expiresAt, String groupUUID, Long dealId, Long userIdToRemove) {
        super(userId, confirmationUUID, isConfirmed, expiresAt);
        this.groupUUID = groupUUID;
        this.dealId = dealId;
        this.userIdToRemove = userIdToRemove;
    }

    @Column(name = "GROUP_UUID", nullable = false, length = 50)
    private String groupUUID;

    @Column(name = "DEAL_ID", nullable = false)
    private Long dealId;

    @Column(name = "USER_ID_TO_REMOVE", nullable = false)
    private Long userIdToRemove;

}
