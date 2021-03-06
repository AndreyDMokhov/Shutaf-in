package com.shutafin.controller;

import com.shutafin.core.service.*;
import com.shutafin.model.entities.User;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.account.AccountInitializationResponse;
import com.shutafin.model.web.common.LanguageWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/initialization")
@Slf4j
public class AccountInitializationController {

    private InitializationService initializationService;
    private UserInfoService userInfoService;
    private UserLanguageService userLanguageService;
    private UserService userService;
    private UserFilterService userFilterService;

    @Autowired
    public AccountInitializationController(
            InitializationService initializationService,
            UserInfoService userInfoService,
            UserLanguageService userLanguageService,
            UserService userService,
            UserFilterService userFilterService) {
        this.initializationService = initializationService;
        this.userInfoService = userInfoService;
        this.userLanguageService = userLanguageService;
        this.userService = userService;
        this.userFilterService = userFilterService;
    }

    @RequestMapping(value = "/languages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<LanguageWeb> getLanguages() {
        log.debug("/initialization/languages");
        return initializationService.findAllLanguages();
    }


    @RequestMapping(value = "{userId}/all", method = RequestMethod.GET)
    public AccountInitializationResponse getInitializationResponse(@PathVariable("userId") Long userId) {
        User user = userService.findUserById(userId);
        Language language = userLanguageService.findUserLanguage(user);

        return AccountInitializationResponse
                .builder()
                .userProfile(userInfoService.getUserInfo(user))
                .cities(initializationService.findAllCitiesByLanguage(language))
                .countries(initializationService.findAllCountriesByLanguage(language))
                .genders(initializationService.findAllGendersByLanguage(language))
                .filters(userFilterService.getUserFilters(user.getId()))
                .build();
    }
}

