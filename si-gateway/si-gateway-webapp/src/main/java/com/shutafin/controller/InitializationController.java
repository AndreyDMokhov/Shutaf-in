package com.shutafin.controller;

import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.QuestionAnswersResponse;
import com.shutafin.model.web.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.model.web.UserQuestionExtendedAnswersWeb;
import com.shutafin.model.web.initialization.CityResponseDTO;
import com.shutafin.model.web.initialization.CountryResponseDTO;
import com.shutafin.model.web.initialization.GenderResponseDTO;
import com.shutafin.model.web.user.FiltersWeb;
import com.shutafin.model.web.initialization.QuestionImportanceDTO;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.service.*;
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
    //TODO moved to matching microservice
    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserLanguageService userLanguageService;

    @Autowired
    private UserSearchService userSearchService;

    //TODO moved to matching microservice
    @Autowired
    private QuestionExtendedService questionExtendedService;
    //TODO moved to matching microservice
    @Autowired
    private UserQuestionExtendedAnswerService userQuestionExtendedAnswerService;

    @Autowired
    private ChatInfoService chatInfoService;


    @NoAuthentication
    @RequestMapping(value = "/languages", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Language> getLanguages() {
        log.debug("/initialization/languages");
        return initializationService.findAllLanguages();
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public InitializationResponse getInitializationResponse(@AuthenticatedUser User user) {
        Language language = userLanguageService.findUserLanguage(user);

        return InitializationResponse
                .builder()
                .userProfile(userInfoService.getUserInfo(user))
                .cities(initializationService.findAllCitiesByLanguage(language))
                .countries(initializationService.findAllCountriesByLanguage(language))
                .genders(initializationService.findAllGendersByLanguage(language))
                .questionAnswersResponses(userMatchService.getUserQuestionsAnswers(language))
                .selectedAnswersResponses(userMatchService.getUserQuestionsSelectedAnswers(user))
                .questionExtendedWithAnswers(questionExtendedService.getQuestionsExtendedWithAnswers(language))
                .questionImportanceList(questionExtendedService.getQuestionImportanceList(language))
                .selectedExtendedAnswersResponses(userQuestionExtendedAnswerService.getSelectedQuestionExtendedAnswers(user))
                .listOfChats(chatInfoService.getListChats(user))
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
    private List<QuestionAnswersResponse> questionAnswersResponses;
    private List<QuestionSelectedAnswersResponse> selectedAnswersResponses;
    private List<QuestionExtendedWithAnswersLocaleWeb> questionExtendedWithAnswers;
    private List<QuestionImportanceDTO> questionImportanceList;
    private List<UserQuestionExtendedAnswersWeb> selectedExtendedAnswersResponses;
    private List<ChatWithUsersListDTO> listOfChats;
    private FiltersWeb filters;

}