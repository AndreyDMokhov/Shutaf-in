package com.shutafin;

import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class GenerateDummyQuestionsService {

    private static final String URL_ACCOUNT_STATUS = "http://localhost:8000/users/account-status?";
    private static final String URL_MATCHING = "http://localhost:8300/matching/";
    private static final String URL_MATCHING_EXTENDED = "http://localhost:8300/matching/extended/";

    private RestTemplateService restTemplateService;

    public GenerateDummyQuestionsService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @Async
    public CompletableFuture<String> generateQuestions(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            updateUserAccountStatus(userIdFrom, userIdTo);
            addQuestionsMain(userIdFrom, userIdTo);
            addQuestionsExtended(userIdFrom, userIdTo);
        }
        return CompletableFuture.completedFuture(null);
    }

    private void updateUserAccountStatus(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            String url = URL_ACCOUNT_STATUS + "userId=" + i + "&status=" + AccountStatus.COMPLETED_REQUIRED_MATCHING.getCode();
            restTemplateService.sendRequest(null, url, HttpMethod.GET);
        }
    }

    private void addQuestionsMain(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            restTemplateService.sendRequest(getListUserQuestionAnswerDTO(), URL_MATCHING + i, HttpMethod.POST);
        }
    }

    private List<UserQuestionAnswerDTO> getListUserQuestionAnswerDTO() {
        List<UserQuestionAnswerDTO> userQuestionAnswerDTOList = new ArrayList<>(4);
        for (int i = 1; i <= 4; i++) {
            userQuestionAnswerDTOList.add(UserQuestionAnswerDTO.builder()
                    .questionId(i)
                    .answerId(i == 4 ? 11 : i * 2)
                    .build());
        }
        return userQuestionAnswerDTOList;
    }

    private void addQuestionsExtended(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            restTemplateService.sendRequest(getListUserQuestionExtendedAnswersWeb(), URL_MATCHING_EXTENDED + i, HttpMethod.POST);
        }
    }

    private List<UserQuestionExtendedAnswersWeb> getListUserQuestionExtendedAnswersWeb() {
        List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList = new ArrayList<>(4);
        for (int i = 1; i <= 4; i++) {
            userQuestionExtendedAnswersWebList.add(UserQuestionExtendedAnswersWeb.builder()
                    .questionId(i)
                    .answersId(Arrays.asList(i * 3))
                    .questionImportanceId(1)
                    .build());
        }
        return userQuestionExtendedAnswersWebList;
    }

}
