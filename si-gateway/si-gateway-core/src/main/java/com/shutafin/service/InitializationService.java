package com.shutafin.service;

import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.initialization.InitializationResponse;

import java.util.List;

public interface InitializationService {
    List<LanguageWeb> findAllLanguages();
    InitializationResponse getInitializationResponse(Long userId);
}
