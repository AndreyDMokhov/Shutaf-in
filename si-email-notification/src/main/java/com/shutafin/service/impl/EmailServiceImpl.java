package com.shutafin.service.impl;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.EmailReason;
import com.shutafin.service.EmailService;
import com.shutafin.service.confirmations.BaseEmailInterface;
import com.shutafin.service.response.BaseConfirmationResponseInterface;
import com.shutafin.service.validation.BaseValidLinkInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Map<String, BaseEmailInterface> mapSendEmail;

    @Autowired
    private Map<String, BaseValidLinkInterface> mapValidLink;

    @Autowired
    private Map<String, BaseConfirmationResponseInterface> mapConfirmationResponse;

    @Override
    @Transactional
    public void sendEmail(EmailNotificationWeb emailNotificationWeb) {

        EmailReason emailReason = emailNotificationWeb.getEmailReason();
        mapSendEmail.get(emailReason.getPropertyPrefix()).send(emailNotificationWeb);

    }

    @Override
    public Object getValidLink(String link, EmailReason emailReason) {

        return mapValidLink.get(emailReason.getPropertyPrefix() + "ValidLink").validate(link);

    }

    @Override
    public Object getConfirmationResponse(String link, EmailReason emailReason) {

        return mapConfirmationResponse.get(emailReason.getPropertyPrefix() + "ConfirmationResponse").getResponse(link);

    }

}