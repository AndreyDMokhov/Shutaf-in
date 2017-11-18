package com.shutafin.controller;

import com.shutafin.core.service.InitializationService;
import com.shutafin.core.service.UserInfoService;
import com.shutafin.core.service.UserLanguageService;
import com.shutafin.core.service.UserSearchService;
import com.shutafin.model.entities.User;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.locale.CityResponseDTO;
import com.shutafin.model.web.locale.CountryResponseDTO;
import com.shutafin.model.web.locale.GenderResponseDTO;
import com.shutafin.model.web.user.FiltersWeb;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    private UserSearchService userSearchService;

    @RequestMapping(value = "/languages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Language> getLanguages() {
        log.debug("/initialization/languages");
        return initializationService.findAllLanguages();
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public InitializationResponse getInitializationResponse(User user) {
        Language language = userLanguageService.findUserLanguage(user);

        return InitializationResponse
                .builder()
                .userProfile(userInfoService.getUserInfo(user))
                .cities(initializationService.findAllCitiesByLanguage(language))
                .countries(initializationService.findAllCountriesByLanguage(language))
                .genders(initializationService.findAllGendersByLanguage(language))
                .filters(
                        new FiltersWeb(
                                userSearchService.getCitiesForFilter(user),
                                userSearchService.getGenderForFilter(user),
                                userSearchService.getAgeRangeForFilter(user)))
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
    private FiltersWeb filters;

}
