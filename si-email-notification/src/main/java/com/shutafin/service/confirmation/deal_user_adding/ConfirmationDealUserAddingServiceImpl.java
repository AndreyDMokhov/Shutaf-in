package com.shutafin.service.confirmation.deal_user_adding;

import com.shutafin.model.entity.confirmation.deal_user_adding.ConfirmationDealUserAdding;
import com.shutafin.repository.deal_user_adding.ConfirmationDealUserAddingRepository;
import com.shutafin.service.confirmation.AbstractConfirmationService;
import com.shutafin.service.confirmation.ConfirmationDealUserAddingService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationDealUserAddingServiceImpl extends AbstractConfirmationService implements ConfirmationDealUserAddingService {

    private ConfirmationDealUserAddingRepository confirmationDealUserAddingRepository;

    @Autowired
    public ConfirmationDealUserAddingServiceImpl(ConfirmationDealUserAddingRepository confirmationDealUserAddingRepository) {
        this.confirmationDealUserAddingRepository = confirmationDealUserAddingRepository;
    }

    @Override
    public ConfirmationDealUserAdding get(Long dealId, Long userId, Long userIdToAdd, String groupUUID) {
        return ConfirmationDealUserAdding.builder()
                .dealId(dealId)
                .userId(userId)
                .userIdToAdd(userIdToAdd)
                .groupUUID(groupUUID)
                .confirmationUUID(UUID.randomUUID().toString())
                .isConfirmed(false)
                .expiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION))
                .build();
    }

    @Override
    public Integer getCountUsersSend(String groupUUID) {
        return confirmationDealUserAddingRepository.countByGroupUUID(groupUUID);
    }

    @Override
    public Integer getCountUsersConfirmed(String groupUUID) {
        return confirmationDealUserAddingRepository.countByGroupUUIDAndIsConfirmedIsTrue(groupUUID);
    }

    @Override
    public ConfirmationDealUserAdding save(ConfirmationDealUserAdding confirmation) {
        return confirmationDealUserAddingRepository.save(confirmation);
    }

    @Override
    public ConfirmationDealUserAdding getConfirmed(String link) {
        return (ConfirmationDealUserAdding)getConfirmed(confirmationDealUserAddingRepository.findByConfirmationUUID(link));
    }

    @Override
    public void revertConfirmation(String link) {
        ConfirmationDealUserAdding confirmation = confirmationDealUserAddingRepository.findByConfirmationUUID(link);
        if (confirmation != null) {
            confirmation.setIsConfirmed(false);
            confirmationDealUserAddingRepository.save(confirmation);
        }

    }
}
