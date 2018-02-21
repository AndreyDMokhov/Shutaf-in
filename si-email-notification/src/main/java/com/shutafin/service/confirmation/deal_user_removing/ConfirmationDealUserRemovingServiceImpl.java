package com.shutafin.service.confirmation.deal_user_removing;

import com.shutafin.model.entity.confirmation.deal_user_removing.ConfirmationDealUserRemoving;
import com.shutafin.repository.deal_user_removing.ConfirmationDealUserRemovingRepository;
import com.shutafin.service.confirmation.ConfirmationDealUserRemovingService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationDealUserRemovingServiceImpl implements ConfirmationDealUserRemovingService {

    private ConfirmationDealUserRemovingRepository confirmationDealUserRemovingRepository;

    @Autowired
    public ConfirmationDealUserRemovingServiceImpl(ConfirmationDealUserRemovingRepository confirmationDealUserRemovingRepository) {
        this.confirmationDealUserRemovingRepository = confirmationDealUserRemovingRepository;
    }

    @Override
    public ConfirmationDealUserRemoving get(Long dealId, Long userId, Long userIdToRemove, String groupUUID) {
        return ConfirmationDealUserRemoving.builder()
                .dealId(dealId)
                .userId(userId)
                .userIdToRemove(userIdToRemove)
                .groupUUID(groupUUID)
                .confirmationUUID(UUID.randomUUID().toString())
                .isConfirmed(false)
                .expiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION))
                .build();
    }

    @Override
    public Integer getCountUsersSend(String groupUUID) {
        return confirmationDealUserRemovingRepository.countByGroupUUID(groupUUID);
    }

    @Override
    public Integer getCountUsersConfirmed(String groupUUID) {
        return confirmationDealUserRemovingRepository.countByGroupUUIDAndIsConfirmedIsTrue(groupUUID);
    }

    @Override
    public ConfirmationDealUserRemoving save(ConfirmationDealUserRemoving confirmation) {
        return confirmationDealUserRemovingRepository.save(confirmation);
    }

    @Override
    public ConfirmationDealUserRemoving getConfirmed(String link) {
        return confirmationDealUserRemovingRepository.findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(link, new Date());
    }

}
