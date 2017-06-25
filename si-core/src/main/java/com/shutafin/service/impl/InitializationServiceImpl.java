package com.shutafin.service.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.LanguageWeb;
import com.shutafin.repository.infrastructure.LanguageRepository;
import com.shutafin.service.initialization.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by evgeny on 6/23/2017.
 */
@Service
@Transactional
public class InitializationServiceImpl implements InitializationService {

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<String, List> findAllConstants() {
        HashMap<String, List> constants = new HashMap<>();

        constants.put("languages", getAllLanguages());

        return constants;
    }

    private ArrayList<LanguageWeb> getAllLanguages() {
        List<Language> languages = languageRepository.findAll();
        ArrayList<LanguageWeb> languageWebList = new ArrayList<>();
        for(Language lang : languages){
            if (lang.getActive()){
                languageWebList.add(getLanguageInfoWeb(lang));
            }
        }
        return languageWebList;
    }

    private LanguageWeb getLanguageInfoWeb(Language lang) {
        return new LanguageWeb(lang.getId(), lang.getDescription(), lang.getLanguageNativeName());
    }
}
