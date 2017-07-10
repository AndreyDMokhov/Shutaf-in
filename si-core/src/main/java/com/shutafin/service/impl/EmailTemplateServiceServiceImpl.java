package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.service.EmailTemplateService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.apache.commons.lang3.Validate.*;


@Service
public class EmailTemplateServiceServiceImpl implements EmailTemplateService {

    private static final String HEADER_SUFFIX = ".header";
    private static final String SECTION_SUFFIX = ".section";

    @Override
    public BaseTemplate getTemplate(EmailReason emailReason, Language language, String link) {
        notNull(emailReason);
        notNull(language);
        notBlank(link);

        Properties properties = getProperties(language);

        String prefix = emailReason.getPropertyPrefix();

        String header = properties.getProperty(prefix + HEADER_SUFFIX);

        String section = properties
                            .getProperty(prefix + SECTION_SUFFIX)
                            .replace("${link}", link);

        String signature = properties.getProperty("common.signature");

        return new BaseTemplate(header, section, signature);
    }

    @Override
    public EmailMessage getEmailMessage(User user, EmailReason emailReason, Language language, String link) {
        notNull(user);
        notNull(emailReason);
        notNull(language);
        notBlank(link);

        return new EmailMessage(user, getTemplate(
                                            emailReason,
                                            language,
                                            link
        ));
    }

    private Properties getProperties(Language language) {
        Properties properties = new Properties();
        InputStream is = null;

        StringBuilder builder = new StringBuilder();
        builder.append("smtp.");
        builder.append(language.getDescription());
        builder.append("-template.properties");
        is = getClass().getClassLoader().getResourceAsStream(builder.toString());


        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unexpected error occurred");
        }

        return properties;
    }



}
