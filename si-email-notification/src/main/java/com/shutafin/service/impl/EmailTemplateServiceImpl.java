package com.shutafin.service.impl;

import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.UserImageSource;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import com.shutafin.service.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
@Slf4j
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private static final String HEADER_SUFFIX = ".header";
    private static final String SECTION_SUFFIX = ".section";

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    private BaseTemplate getTemplate(EmailReason emailReason, String languageDescription, String link) {
        notNull(emailReason);
        notNull(languageDescription);
        notBlank(link);

        Properties properties = getProperties(languageDescription);

        String prefix = emailReason.getPropertyPrefix();

        String header = properties.getProperty(prefix + HEADER_SUFFIX);

        String section = properties
                .getProperty(prefix + SECTION_SUFFIX)
                .replace("${link}", link);

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
                        link),
                imageSources
        );
    }

    @Override
    public EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String emailChange, String confirmationUrl) {

        String serverAddress = discoveryRoutingService.getRoute(RouteDirection.SI_GATEWAY);
        String urlLink = serverAddress + confirmationUrl + link;
        return getEmailMessage(emailNotificationWeb, urlLink, (Map<String, byte[]>) null, emailChange);

    }

    @Override
    public EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String confirmationUrl) {
        return getEmailMessage(emailNotificationWeb, link, emailNotificationWeb.getEmailTo(), confirmationUrl);
    }

    @Override
    public EmailMessage getEmailMessageMatchingCandidates(EmailNotificationWeb emailNotificationWeb, String urlProfile, String urlSearch) {

        String urlLink = "";
        String serverAddress = discoveryRoutingService.getRoute(RouteDirection.SI_GATEWAY);
        Map<String, byte[]> imageSources = new TreeMap<>();

        for (UserImageSource userImageSource : emailNotificationWeb.getUserImageSources()) {
            imageSources.put(userImageSource.getUserId().toString(), userImageSource.getImageSource());
            urlLink = urlLink.concat(getUserImageLink(userImageSource, serverAddress, urlProfile));
        }
        urlLink += getSearchLink(serverAddress, urlSearch);
        return getEmailMessage(emailNotificationWeb, urlLink, imageSources);

    }

    private String getUserImageLink(UserImageSource userImageSource, String serverAddress, String urlProfile) {
        return ""
                .concat("<p style=\"font-size:14px\"><a href=\"")
                .concat(serverAddress)
                .concat(urlProfile)
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

    private String getSearchLink(String serverAddress, String urlSearch) {
        return ""
                .concat("<p><a href=\"")
                .concat(serverAddress)
                .concat(urlSearch)
                .concat("\">");
    }

}