package com.shutafin.service;

import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;

import java.util.Map;
import java.util.Set;

public interface EmailImageSourceService {

    Set<EmailImageSource> get(EmailNotificationLog emailNotificationLog, Map<String, byte[]> imageSources);

    void save(Set<EmailImageSource> emailImageSources);

}
