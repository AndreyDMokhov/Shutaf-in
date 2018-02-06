package com.shutafin.service;

import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Set;

/**
 * Created by evgeny on 2/7/2018.
 */
public interface AsyncSenderEmailService {
    void send(MimeMessage mimeMessage, EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources);
    MimeMessage getMimeMessage(String email, String html, String header, Map<String, byte[]> imageSources);
}
