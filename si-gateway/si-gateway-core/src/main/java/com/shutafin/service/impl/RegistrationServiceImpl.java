package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.RegistrationConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.repository.account.RegistrationConfirmationRepository;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.EmailNotificationSenderService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.EnvironmentConfigurationService;
import com.shutafin.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@Transactional
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {


    @Autowired
    private EmailNotificationSenderService mailSenderService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private RegistrationConfirmationRepository registrationConfirmationRepository;

    @Autowired
    private EnvironmentConfigurationService environmentConfigurationService;

    @Autowired
    private DiscoveryRoutingService routingService;


    @Override
    public void save(AccountRegistrationRequest registrationRequestWeb) {

        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) + "/users/registration/request";
        new RestTemplate().postForEntity(url, registrationRequestWeb, Void.class);

        //todo ms-email
//        sendConfirmRegistrationEmail(user, userAccount);
    }

    @Override
    @Transactional
    public AccountUserWeb confirmRegistration(String link) {
        //todo ms-email
        RegistrationConfirmation registrationConfirmation = registrationConfirmationRepository.findByUrlLink(link);

        if (registrationConfirmation == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }

        registrationConfirmation.setIsConfirmed(true);
        registrationConfirmationRepository.save(registrationConfirmation);


        String url = routingService.getRoute(RouteDirection.SI_ACCOUNT) +
                String.format("/users/registration/confirm/%d", registrationConfirmation.getUser().getId());

        return new RestTemplate().getForEntity(url, AccountUserWeb.class).getBody();
    }

    private void sendConfirmRegistrationEmail(User user, UserAccount userAccount) {
        RegistrationConfirmation registrationConfirmation = new RegistrationConfirmation();
        registrationConfirmation.setUser(user);
        registrationConfirmation.setIsConfirmed(false);
        registrationConfirmation.setUrlLink(UUID.randomUUID().toString());
        registrationConfirmationRepository.save(registrationConfirmation);

        String link = environmentConfigurationService.getServerAddress() + "/#/users/registration/confirmation/" + registrationConfirmation.getUrlLink();
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(registrationConfirmation.getUser(), EmailReason.REGISTRATION_CONFIRMATION, userAccount.getLanguage(), link);
        mailSenderService.sendEmail(emailMessage, EmailReason.REGISTRATION_CONFIRMATION);
    }

}