package com.shutafin.service.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.LanguageInfoWeb;
import com.shutafin.repository.infrastructure.LanguageRepository;
import com.shutafin.service.initialization.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public Map<String, Object> findAllConstants() {
        HashMap<String, Object> constants = new HashMap<>();

        constants.put("languages", getAllLanguages());

        return constants;
    }

    private ArrayList<LanguageInfoWeb> getAllLanguages() {
        List<Language> languages = languageRepository.findAll();
        ArrayList<LanguageInfoWeb> languageInfoWebList = new ArrayList<>();
        for(Language lang : languages){
            if (lang.getActive()){
                languageInfoWebList.add(getLanguageInfoWeb(lang));
            }
        }
        return languageInfoWebList;
    }

    private LanguageInfoWeb getLanguageInfoWeb(Language lang) {
        return new LanguageInfoWeb(lang.getId(), lang.getDescription(), lang.getLanguageNativeName());
    }
}
