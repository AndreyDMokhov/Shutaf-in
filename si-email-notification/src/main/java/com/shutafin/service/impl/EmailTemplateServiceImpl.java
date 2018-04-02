package com.shutafin.service.impl;

import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.*;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.apache.commons.lang3.Validate.notNull;

@Service
@Slf4j
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private static final String HEADER_SUFFIX = ".header";
    private static final String SECTION_SUFFIX = ".section";

    @Value("${domain.name}")
    private String url;

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    private BaseTemplate getTemplate(EmailReason emailReason, String languageDescription, Map<String, String> textReplace) {
        notNull(emailReason);
        notNull(languageDescription);
        notNull(textReplace);

        Properties properties = getProperties(languageDescription);

        String prefix = emailReason.getPropertyPrefix();

        String header = properties.getProperty(prefix + HEADER_SUFFIX);

        String section = properties.getProperty(prefix + SECTION_SUFFIX);

        for (Map.Entry<String, String> mapText : textReplace.entrySet()) {
            section = section.replace("${" + mapText.getKey() + "}", mapText.getValue());
        }

        String signature = properties.getProperty("common.signature");

        return new BaseTemplate(header, section, signature);
    }

    private Properties getProperties(String languageDescription) {
        Properties properties = new Properties();
        InputStream is = null;

        StringBuilder builder = new StringBuilder()
                .append("smtp.")
                .append(languageDescription)
                .append("-template.properties");

        is = getClass().getClassLoader().getResourceAsStream(builder.toString());

        try {
            properties.load(is);
        } catch (IOException e) {
            log.error("Unexpected error occurred: ", e);
            throw new IllegalStateException("Unexpected error occurred");
        }

        return properties;
    }

    @Override
    public EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources) {
        return getEmailMessage(emailNotificationWeb, link, imageSources, null);
    }

    @Override
    public EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources, String emailChange) {
        return new EmailMessage(
                emailNotificationWeb.getUserId(),
                emailChange == null ? emailNotificationWeb.getEmailTo() : emailChange,
                getTemplate(
                        emailNotificationWeb.getEmailReason(),
                        emailNotificationWeb.getLanguageCode(),
                        new HashMap<String, String>() {{
                            put("link", link);
                        }}
                ),
                imageSources
        );
    }

    @Override
    public EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String emailChange, String confirmationUrl) {

        String urlLink = getServerAddress() + confirmationUrl + link;
        return getEmailMessage(emailNotificationWeb, urlLink, (Map<String, byte[]>) null, emailChange);

    }

    @Override
    public EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String confirmationUrl) {
        return getEmailMessage(emailNotificationWeb, link, emailNotificationWeb.getEmailTo(), confirmationUrl);
    }

    @Override
    public EmailMessage getEmailMessageMatchingCandidates(EmailNotificationWeb emailNotificationWeb, String urlProfile, String urlSearch) {

        String urlLink = "";
        String serverAddress = getServerAddress();
        Map<String, byte[]> imageSources = new TreeMap<>();

        for (EmailUserImageSource emailUserImageSource : emailNotificationWeb.getEmailUserImageSources()) {
            imageSources.put(emailUserImageSource.getUserId().toString(), Base64.getDecoder().decode(emailUserImageSource.getImageSource()));
            urlLink = urlLink.concat(getUserImageLink(emailUserImageSource, serverAddress, urlProfile));
        }
        urlLink += getSearchLink(serverAddress, urlSearch);
        return getEmailMessage(emailNotificationWeb, urlLink, imageSources);

    }

    @Override
    public EmailMessage getEmailMessageDeal(EmailNotificationDealWeb emailNotificationDealWeb, EmailUserLanguage emailUserLanguage,
                                            String confirmationUUID, String confirmationUrl, String urlProfile) {

        String serverAddress = getServerAddress();
        String urlLink = serverAddress + confirmationUrl + confirmationUUID;
        Map<String, byte[]> imageSources = new TreeMap<>();

        Map<String, String> textReplace = new HashMap<>();
        EmailUserImageSource userOrigin = emailNotificationDealWeb.getUserOrigin();
        String userOriginUrl = getUserImageLink(userOrigin, serverAddress, urlProfile);
        imageSources.put(userOrigin.getUserId().toString(), Base64.getDecoder().decode(userOrigin.getImageSource()));
        textReplace.put("userOrigin", userOriginUrl);
        if (emailNotificationDealWeb.getUserToChange() != null) {
            EmailUserImageSource userToChange = emailNotificationDealWeb.getUserToChange();
            String userToChangeUrl = getUserImageLink(userToChange, serverAddress, urlProfile);
            imageSources.put(userToChange.getUserId().toString(), Base64.getDecoder().decode(userToChange.getImageSource()));
            textReplace.put("userToChange", userToChangeUrl);
        }
        textReplace.put("dealTitle", emailNotificationDealWeb.getDealTitle());
        textReplace.put("link", urlLink);

        return new EmailMessage(
                emailUserLanguage.getUserId(),
                emailUserLanguage.getEmail(),
                getTemplate(
                        emailNotificationDealWeb.getEmailReason(),
                        emailUserLanguage.getLanguageCode(),
                        textReplace
                ),
                imageSources
        );
    }

    private String getServerAddress() {
        return discoveryRoutingService.getRoute(RouteDirection.SI_GATEWAY);
    }

    private String getUserImageLink(EmailUserImageSource emailUserImageSource, String serverAddress, String urlProfile) {
        return ""
                .concat("<p style=\"font-size:14px\"><a href=\"")
                .concat(serverAddress)
                .concat(urlProfile)
                .concat(emailUserImageSource.getUserId().toString())
                .concat("\"> ")
                .concat(emailUserImageSource.getFirstName())
                .concat(" ")
                .concat(emailUserImageSource.getLastName())
                .concat(" <br><img src=\"cid:")
                .concat(emailUserImageSource.getUserId().toString())
                .concat("\" style=\"width:128px;height:128px;\">")
                .concat("</a></p>");
    }

    private String getSearchLink(String serverAddress, String urlSearch) {
        return ""
                .concat("<p><a href=\"")
                .concat(serverAddress)
                .concat(urlSearch)
                .concat("\">");
    }

}