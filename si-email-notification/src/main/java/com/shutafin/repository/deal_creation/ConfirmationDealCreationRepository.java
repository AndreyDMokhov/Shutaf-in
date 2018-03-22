package com.shutafin.repository.deal_creation;

import com.shutafin.model.entity.confirmation.deal_creation.ConfirmationDealCreation;
import com.shutafin.repository.BaseConfirmationRepository;

public interface ConfirmationDealCreationRepository extends BaseConfirmationRepository<ConfirmationDealCreation, Long> {

    Integer countByDealId(Long dealId);

    Integer countByDealIdAndIsConfirmedIsTrue(Long dealId);

    ConfirmationDealCreation findByConfirmationUUID(String link);

}
