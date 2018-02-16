package com.shutafin.service.confirmation;

import com.shutafin.model.entity.confirmation.deal_user_adding.ConfirmationDealUserAdding;

public interface ConfirmationDealUserAddingService extends BaseConfirmationService<ConfirmationDealUserAdding> {

    ConfirmationDealUserAdding get(Long dealId, Long userIdToAdd, String groupUUID);

    Integer getCountUsersSend(String groupUUID);

    Integer getCountUsersConfirmed(String groupUUID);

}
