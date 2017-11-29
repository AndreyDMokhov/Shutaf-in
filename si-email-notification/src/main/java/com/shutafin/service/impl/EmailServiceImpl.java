package com.shutafin.service.impl;

import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.service.EmailService;
import com.shutafin.service.response.BaseConfirmationResponseInterface;
import com.shutafin.service.sender.BaseEmailInterface;
import com.shutafin.service.validation.BaseValidLinkInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Map<String, BaseEmailInterface> mapSenderEmail;

    @Autowired
    private Map<String, BaseValidLinkInterface> mapValidLink;

    @Autowired
    private Map<String, BaseConfirmationResponseInterface> mapConfirmationResponse;

    @Override
    @Transactional
    public void sendEmail(EmailNotificationWeb emailNotificationWeb) {

        String emailReasonPrefix = emailNotificationWeb.getEmailReason().getPropertyPrefix();
        getResourceNotFoundException(emailReasonPrefix, "mapSenderEmail", mapSenderEmail);
        mapSenderEmail.get(emailReasonPrefix).send(emailNotificationWeb);

    }

    @Override
    public Object getValidLink(String link, EmailReason emailReason) {

        String emailReasonPrefix = emailReason.getPropertyPrefix() + "ValidLink";
        getResourceNotFoundException(emailReasonPrefix, "mapValidLink", mapValidLink);
        return mapValidLink.get(emailReasonPrefix).validate(link);

    }

    @Override
    public Object getConfirmationResponse(String link, EmailReason emailReason) {

        String emailReasonPrefix = emailReason.getPropertyPrefix() + "ConfirmationResponse";
        getResourceNotFoundException(emailReasonPrefix, "mapConfirmationResponse", mapConfirmationResponse);
        return mapConfirmationResponse.get(emailReasonPrefix).getResponse(link);

    }

    private void getResourceNotFoundException(String emailReasonPrefix, String mapName, Map map) {

        boolean equal = false;

        Set keySet = map.keySet();
        for (Object o : keySet) {
            if (emailReasonPrefix.equals(o)) {
                equal = true;
            }
        }

        if (!equal) {
            log.warn("Resource not found exception:");
            log.warn("{} was not found component {}", mapName, emailReasonPrefix);
            throw new ResourceNotFoundException();
        }

    }

}