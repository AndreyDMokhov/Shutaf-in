package com.shutafin.repository.deal_user_adding;

import com.shutafin.model.entity.confirmation.deal_user_adding.ConfirmationDealUserAdding;
import com.shutafin.repository.BaseConfirmationRepository;

public interface ConfirmationDealUserAddingRepository extends BaseConfirmationRepository<ConfirmationDealUserAdding, Long> {

    Integer countByGroupUUID(String groupUUID);

    Integer countByGroupUUIDAndIsConfirmedIsTrue(String groupUUID);

}
