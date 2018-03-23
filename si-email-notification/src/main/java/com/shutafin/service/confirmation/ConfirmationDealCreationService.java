package com.shutafin.service.confirmation;

import com.shutafin.model.entity.confirmation.deal_creation.ConfirmationDealCreation;

public interface ConfirmationDealCreationService extends BaseConfirmationService<ConfirmationDealCreation> {

    ConfirmationDealCreation get(Long dealId, Long userId);

    Integer getCountUsersSend(Long dealId);

    Integer getCountUsersConfirmed(Long dealId);

}
