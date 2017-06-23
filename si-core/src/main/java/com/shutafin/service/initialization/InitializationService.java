package com.shutafin.service.initialization;

import com.shutafin.model.web.initialization.LanguageInfoWeb;

import java.util.List;
import java.util.Map;

/**
 * Created by evgeny on 6/23/2017.
 */
public interface InitializationService {
    public Map<String, Object> findAllConstants();
}
