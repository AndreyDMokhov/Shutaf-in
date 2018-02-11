package com.shutafin.service.response.deal_user_adding;

import com.shutafin.model.entity.confirmation.deal_user_adding.ConfirmationDealUserAdding;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.email.response.EmailDealUserAddingResponse;
import com.shutafin.service.confirmation.ConfirmationDealUserAddingService;
import com.shutafin.service.response.BaseConfirmationResponseInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dealUserAddingConfirmationResponse")
@Slf4j
public class ConfirmationResponseDealUserAddingComponent implements BaseConfirmationResponseInterface<EmailDealUserAddingResponse> {

    @Autowired
    private ConfirmationDealUserAddingService confirmationDealUserAddingService;

    @Override
    public EmailDealUserAddingResponse getResponse(String link) {

        ConfirmationDealUserAdding confirmation = confirmationDealUserAddingService.getConfirmed(link);
        if (confirmation == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }

        confirmation.setIsConfirmed(true);
        confirmationDealUserAddingService.save(confirmation);

        Integer countUsersSend = confirmationDealUserAddingService.getCountUsersSend(confirmation.getGroupUUID());
        Integer countUsersConfirmed = confirmationDealUserAddingService.getCountUsersConfirmed(confirmation.getGroupUUID());

        return EmailDealUserAddingResponse.builder()
                .dealId(confirmation.getDealId())
                .userIdToAdd(confirmation.getUserIdToAdd())
                .countUsersSend(countUsersSend)
                .countUsersConfirmed(countUsersConfirmed)
                .isConfirmed(countUsersSend - countUsersConfirmed == 0 ? true : false)
                .build();
    }

}
