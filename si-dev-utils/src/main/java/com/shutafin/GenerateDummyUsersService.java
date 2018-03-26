package com.shutafin;

import com.shutafin.model.web.account.AccountRegistrationRequest;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class GenerateDummyUsersService {

    private static final String URL_REGISTRATION = "http://localhost:8080/api/users/registration/request";
    private static final String PASSWORD = "11111111";
    private static final Integer USER_LANGUAGE_ID = 1;
    private static final String EMAIL_PREFIX = "tel";
    private static final String EMAIL_SUFFIX = "ran";

    private RestTemplateService restTemplateService;

    public GenerateDummyUsersService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @Async
    public CompletableFuture<String> generateUsers(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            restTemplateService.sendRequest(getRegistrationRequestWeb(i), URL_REGISTRATION, HttpMethod.POST);
        }
        return CompletableFuture.completedFuture(null);
    }

    private AccountRegistrationRequest getRegistrationRequestWeb(int n) {
        return AccountRegistrationRequest.builder()
                .firstName(getFirstName(n))
                .lastName(getLastName(n))
                .email("_" + getEmail(n))
                .password(PASSWORD)
                .userLanguageId(USER_LANGUAGE_ID)
                .build();
    }

    public static String getFirstName(int n){
        return EMAIL_PREFIX + n;
    }

    public static String getLastName(int n){
        return EMAIL_SUFFIX + n;
    }

    public static String getEmail(int number) {
        return EMAIL_PREFIX + number + "@" + EMAIL_SUFFIX;
    }

}
