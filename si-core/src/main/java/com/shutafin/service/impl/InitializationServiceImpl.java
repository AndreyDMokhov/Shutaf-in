package com.shutafin.service.impl;

import com.shutafin.repository.LanguageRepository;
import com.shutafin.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class InitializationServiceImpl implements InitializationService {

    @Autowired
    private LanguageRepository languageRepository;


    @Override
    @Transactional(readOnly = true)
    public List findAllLanguages() {

        return languageRepository.findAll();
    }



}
