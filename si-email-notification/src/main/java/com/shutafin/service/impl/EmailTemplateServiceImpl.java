package com.shutafin.service.impl;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.EmailReason;
import com.shutafin.model.entity.ConfirmationNewEmail;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.service.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@Service
@Slf4j
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private static final String HEADER_SUFFIX = ".header";
    private static final String SECTION_SUFFIX = ".section";

    @Autowired
    private DiscoveryRoutingService discoveryRoutingService;

    @Override
    public BaseTemplate getTemplate(EmailReason emailReason, String languageDescription, String link) {
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
    public EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources, String newEmail) {
        return new EmailMessage(
                emailNotificationWeb.getUserId(),
                newEmail == null ? emailNotificationWeb.getEmailTo() : newEmail,
                getTemplate(
                        emailNotificationWeb.getEmailReason(),
                        emailNotificationWeb.getLanguageCode(),
                        link),
                imageSources
        );
    }

    public EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String newEmail, String confirmationUrl) {

        String serverAddress = discoveryRoutingService.getExternalRoute();
        String urlLink = serverAddress + confirmationUrl + link;
        return getEmailMessage(emailNotificationWeb, urlLink, (Map<String, byte[]>) null, newEmail);

    }

    public EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String confirmationUrl){
        return getEmailMessage(emailNotificationWeb, link, emailNotificationWeb.getEmailTo(), confirmationUrl);
    }

}