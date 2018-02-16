package com.shutafin.service.confirmation;

import com.shutafin.model.entity.confirmation.deal_creation.ConfirmationDealCreation;

public interface ConfirmationDealCreationService extends BaseConfirmationService<ConfirmationDealCreation> {

    ConfirmationDealCreation get(Long dealId);

    Integer getCountUsersSend(Long dealId);

    Integer getCountUsersConfirmed(Long dealId);

}
