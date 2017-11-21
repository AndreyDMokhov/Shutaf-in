package com.shutafin.controller;

import com.shutafin.core.service.*;
import com.shutafin.model.entities.User;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.locale.CityResponseDTO;
import com.shutafin.model.web.locale.CountryResponseDTO;
import com.shutafin.model.web.locale.GenderResponseDTO;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class InitializationController {

    @Autowired
    private InitializationService initializationService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserLanguageService userLanguageService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/languages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Language> getLanguages() {
        log.debug("/initialization/languages");
        return initializationService.findAllLanguages();
    }


    @RequestMapping(value = "{userId}/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public InitializationResponse getInitializationResponse(@PathVariable("userId") Long userId) {
        User user = userService.findUserById(userId);
        Language language = userLanguageService.findUserLanguage(user);

        return InitializationResponse
                .builder()
                .userProfile(userInfoService.getUserInfo(user))
                .cities(initializationService.findAllCitiesByLanguage(language))
                .countries(initializationService.findAllCountriesByLanguage(language))
                .genders(initializationService.findAllGendersByLanguage(language))
                .build();
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
class InitializationResponse {
    private UserInfoResponseDTO userProfile;
    private List<GenderResponseDTO> genders;
    private List<CountryResponseDTO> countries;
    private List<CityResponseDTO> cities;
}
