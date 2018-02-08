package com.shutafin.service.confirmation.deal_creation;

import com.shutafin.model.entity.confirmation.deal_creation.ConfirmationDealCreation;
import com.shutafin.repository.deal_creation.ConfirmationDealCreationRepository;
import com.shutafin.service.confirmation.ConfirmationDealCreationService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConfirmationDealCreationServiceImpl implements ConfirmationDealCreationService {

    private ConfirmationDealCreationRepository confirmationDealCreationRepository;

    @Autowired
    public ConfirmationDealCreationServiceImpl(ConfirmationDealCreationRepository confirmationDealCreationRepository) {
        this.confirmationDealCreationRepository = confirmationDealCreationRepository;
    }

    @Override
    public ConfirmationDealCreation get(Long dealId) {
        return ConfirmationDealCreation.builder()
                .dealId(dealId)
                .confirmationUUID(UUID.randomUUID().toString())
                .isConfirmed(false)
                .expiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION))
                .build();
    }

    @Override
    public Integer getCountUsersSend(Long dealId) {
        return confirmationDealCreationRepository.countByDealId(dealId);
    }

    @Override
    public Integer getCountUsersConfirmed(Long dealId) {
        return confirmationDealCreationRepository.countByDealIdAndIsConfirmedIsTrue(dealId);
    }

    @Override
    public ConfirmationDealCreation save(ConfirmationDealCreation confirmation) {
        return confirmationDealCreationRepository.save(confirmation);
    }

    @Override
    public ConfirmationDealCreation getConfirmed(String link) {
        return confirmationDealCreationRepository.findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(link, new Date());
    }

}
