package com.shutafin;

import com.shutafin.model.web.account.AccountRegistrationRequest;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class GenerateDummyUsersService {

    private static final String URL_REGISTRATION = "http://localhost:8080/api/users/registration/request";
    private static final int COUNT_CHARACTER_NAME = 3;
    private static final String PASSWORD = "11111111";
    private static final Integer USER_LANGUAGE_ID = 1;
    private static final String EMAIL_PREFIX = "tel";
    private static final String EMAIL_SUFFIX = "@ran";

    private static Random random = new Random();

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
                .firstName(getName())
                .lastName(getName())
                .email(getEmail(n))
                .password(PASSWORD)
                .userLanguageId(USER_LANGUAGE_ID)
                .build();
    }

    public static String getName() {
        StringBuilder name = new StringBuilder(COUNT_CHARACTER_NAME);
        for (int i = 0; i < COUNT_CHARACTER_NAME; i++) {
            name.append(getChar());
        }
        return name.toString();
    }

    private static char getChar() {
        return (char) (random.nextInt('z' - 'a' + 1) + 'a');
    }

    public static String getEmail(int number) {
        return EMAIL_PREFIX + Integer.toString(number) + EMAIL_SUFFIX;
    }

}
