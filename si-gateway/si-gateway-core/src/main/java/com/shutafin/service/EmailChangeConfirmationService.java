package com.shutafin.service;

import com.shutafin.model.web.account.AccountEmailChangeValidationRequest;
import com.shutafin.model.web.user.GatewayEmailChangedResponse;

public interface EmailChangeConfirmationService {

    void emailChangeRequest(Long userId, AccountEmailChangeValidationRequest emailChangeConfirmationWeb);

    GatewayEmailChangedResponse emailChangeConfirmation(String link);
}
