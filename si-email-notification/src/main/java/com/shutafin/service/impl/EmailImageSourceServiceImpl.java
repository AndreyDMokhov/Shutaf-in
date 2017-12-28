package com.shutafin.service.impl;

import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.repository.EmailImageSourceRepository;
import com.shutafin.service.EmailImageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class EmailImageSourceServiceImpl implements EmailImageSourceService {

    private EmailImageSourceRepository emailImageSourceRepository;

    @Autowired
    public EmailImageSourceServiceImpl(EmailImageSourceRepository emailImageSourceRepository) {
        this.emailImageSourceRepository = emailImageSourceRepository;
    }

    @Override
    public Set<EmailImageSource> get(EmailNotificationLog emailNotificationLog, Map<String, byte[]> imageSources) {
        Set<EmailImageSource> emailImageSources = new HashSet<>();
        for (Map.Entry<String, byte[]> entry : imageSources.entrySet()) {
            emailImageSources.add(
                    emailImageSourceRepository.save(
                            EmailImageSource
                                    .builder()
                                    .emailNotificationLog(emailNotificationLog)
                                    .contentId(entry.getKey())
                                    .imageSource(entry.getValue())
                                    .build()));
        }
        return emailImageSources;
    }

    @Override
    public void save(Set<EmailImageSource> emailImageSources) {
        emailImageSourceRepository.save(emailImageSources);
    }
}
