package com.shutafin.service.confirmation;

import com.shutafin.model.entity.confirmation.deal_user_removing.ConfirmationDealUserRemoving;

public interface ConfirmationDealUserRemovingService extends BaseConfirmationService<ConfirmationDealUserRemoving> {

    ConfirmationDealUserRemoving get(Long dealId, Long userIdToRemove, String groupUUID);

    Integer getCountUsersSend(String groupUUID);

    Integer getCountUsersConfirmed(String groupUUID);

}
