package com.shutafin.service.confirmations.custom;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.UserImageSource;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.confirmations.EmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component("matchingCandidates")
public class EmailMatchingCandidatesComponent implements EmailInterface {

    private static final String URL_PROFILE = "/#/profile/";
    private static final String URL_SEARCH = "/#/users/search";

    private EmailTemplateService emailTemplateService;
    private DiscoveryRoutingService discoveryRoutingService;
    private SenderEmailMessageService senderEmailMessageService;

    @Autowired
    public EmailMatchingCandidatesComponent(EmailTemplateService emailTemplateService,
                                            DiscoveryRoutingService discoveryRoutingService,
                                            SenderEmailMessageService senderEmailMessageService) {
        this.emailTemplateService = emailTemplateService;
        this.discoveryRoutingService = discoveryRoutingService;
        this.senderEmailMessageService = senderEmailMessageService;
    }

    @Override
    public void send(EmailNotificationWeb emailNotificationWeb) {
        EmailMessage emailMessage = getMatchingCandidatesEmailMessage(emailNotificationWeb);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);
    }

    private EmailMessage getMatchingCandidatesEmailMessage(EmailNotificationWeb emailNotificationWeb) {

        String urlLink = "";
        String serverAddress = discoveryRoutingService.getExternalRoute();
        Map<String, byte[]> imageSources = new TreeMap<>();

        for (UserImageSource userImageSource : emailNotificationWeb.getUserImageSources()) {
            imageSources.put(userImageSource.getUserId().toString(), userImageSource.getImageSource());
            urlLink = urlLink.concat(getUserImageLink(userImageSource, serverAddress));
        }
        urlLink += getSearchLink(serverAddress);
        return emailTemplateService.getEmailMessage(emailNotificationWeb, urlLink, imageSources);

    }

    private String getUserImageLink(UserImageSource userImageSource, String serverAddress) {
        return ""
                .concat("<p style=\"font-size:14px\"><a href=\"")
                .concat(serverAddress)
                .concat(URL_PROFILE)
                .concat(userImageSource.getUserId().toString())
                .concat("\"> ")
                .concat(userImageSource.getFirstName())
                .concat(" ")
                .concat(userImageSource.getLastName())
                .concat(" <br><img src=\"cid:")
                .concat(userImageSource.getUserId().toString())
                .concat("\" style=\"width:128px;height:128px;\">")
                .concat("</a></p>");
    }

    private String getSearchLink(String serverAddress) {
        return ""
                .concat("<p><a href=\"")
                .concat(serverAddress)
                .concat(URL_SEARCH)
                .concat("\">");
    }
}
