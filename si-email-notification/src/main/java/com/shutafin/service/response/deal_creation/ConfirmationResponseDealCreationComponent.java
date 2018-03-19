package com.shutafin.service.response.deal_creation;

import com.shutafin.model.entity.confirmation.deal_creation.ConfirmationDealCreation;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.email.response.EmailDealCreationResponse;
import com.shutafin.service.confirmation.ConfirmationDealCreationService;
import com.shutafin.service.response.BaseConfirmationResponseInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dealCreationConfirmationResponse")
@Slf4j
public class ConfirmationResponseDealCreationComponent implements BaseConfirmationResponseInterface<EmailDealCreationResponse> {

    @Autowired
    private ConfirmationDealCreationService confirmationDealCreationService;

    @Override
    public EmailDealCreationResponse getResponse(String link) {

        ConfirmationDealCreation confirmation = confirmationDealCreationService.getConfirmed(link);
        if (confirmation == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }

        confirmation.setIsConfirmed(true);
        confirmationDealCreationService.save(confirmation);

        Integer countUsersSend = confirmationDealCreationService.getCountUsersSend(confirmation.getDealId());
        Integer countUsersConfirmed = confirmationDealCreationService.getCountUsersConfirmed(confirmation.getDealId());

        return EmailDealCreationResponse.builder()
                .dealId(confirmation.getDealId())
                .userId(confirmation.getUserId())
                .countUsersSend(countUsersSend)
                .countUsersConfirmed(countUsersConfirmed)
                .isConfirmed(countUsersSend - countUsersConfirmed == 0)
                .build();
    }

}
