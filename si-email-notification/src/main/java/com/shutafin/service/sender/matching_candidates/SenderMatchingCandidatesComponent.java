package com.shutafin.service.sender.matching_candidates;

import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.sender.BaseEmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("matchingCandidates")
public class SenderMatchingCandidatesComponent implements BaseEmailInterface {

    private static final String URL_PROFILE = "/#/profile/";
    private static final String URL_SEARCH = "/#/users/search";

    private EmailTemplateService emailTemplateService;
    private DiscoveryRoutingService discoveryRoutingService;
    private SenderEmailMessageService senderEmailMessageService;

    @Autowired
    public SenderMatchingCandidatesComponent(EmailTemplateService emailTemplateService,
                                             DiscoveryRoutingService discoveryRoutingService,
                                             SenderEmailMessageService senderEmailMessageService) {
        this.emailTemplateService = emailTemplateService;
        this.discoveryRoutingService = discoveryRoutingService;
        this.senderEmailMessageService = senderEmailMessageService;
    }

    @Override
    public void send(EmailNotificationWeb emailNotificationWeb) {
        EmailMessage emailMessage = emailTemplateService.getEmailMessageMatchingCandidates(emailNotificationWeb, URL_PROFILE, URL_SEARCH);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb.getEmailReason(), emailMessage);
    }

}
