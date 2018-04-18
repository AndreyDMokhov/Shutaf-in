package com.shutafin.service.response.deal_user_removing;

import com.shutafin.model.entity.confirmation.deal_user_removing.ConfirmationDealUserRemoving;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.email.response.EmailDealUserRemovingResponse;
import com.shutafin.service.confirmation.ConfirmationDealUserRemovingService;
import com.shutafin.service.response.BaseConfirmationResponseInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dealUserRemovingConfirmationResponse")
@Slf4j
public class ConfirmationResponseDealUserRemovingComponent implements BaseConfirmationResponseInterface<EmailDealUserRemovingResponse> {

    @Autowired
    private ConfirmationDealUserRemovingService confirmationDealUserRemovingService;

    @Override
    public EmailDealUserRemovingResponse getResponse(String link) {

        ConfirmationDealUserRemoving confirmation = confirmationDealUserRemovingService.getConfirmed(link);

        confirmation.setIsConfirmed(true);
        confirmationDealUserRemovingService.save(confirmation);

        Integer countUsersSend = confirmationDealUserRemovingService.getCountUsersSend(confirmation.getGroupUUID());
        Integer countUsersConfirmed = confirmationDealUserRemovingService.getCountUsersConfirmed(confirmation.getGroupUUID());

        return EmailDealUserRemovingResponse.builder()
                .dealId(confirmation.getDealId())
                .userOriginId(confirmation.getUserId())
                .userIdToRemove(confirmation.getUserIdToRemove())
                .countUsersSend(countUsersSend)
                .countUsersConfirmed(countUsersConfirmed)
                .isConfirmed(countUsersSend - countUsersConfirmed == 0)
                .build();
    }

    @Override
    public void revertConfirmation(String link) {
        confirmationDealUserRemovingService.revertConfirmation(link);
    }
}
