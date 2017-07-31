package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.entities.types.LanguageEnum;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;


public interface EmailTemplateService {

    BaseTemplate getTemplate(EmailReason emailReason, LanguageEnum language, String link);
    EmailMessage getEmailMessage(User user, EmailReason emailReason, LanguageEnum language, String link);
    EmailMessage getEmailMessage(String emailTo, EmailReason emailReason, LanguageEnum language, String link);
}
