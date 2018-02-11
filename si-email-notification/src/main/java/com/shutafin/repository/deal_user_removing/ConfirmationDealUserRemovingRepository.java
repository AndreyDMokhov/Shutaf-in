package com.shutafin.repository.deal_user_removing;

import com.shutafin.model.entity.confirmation.deal_user_removing.ConfirmationDealUserRemoving;
import com.shutafin.repository.BaseConfirmationRepository;

public interface ConfirmationDealUserRemovingRepository extends BaseConfirmationRepository<ConfirmationDealUserRemoving, Long> {

    Integer countByGroupUUID(String groupUUID);

    Integer countByGroupUUIDAndIsConfirmedIsTrue(String groupUUID);

}
