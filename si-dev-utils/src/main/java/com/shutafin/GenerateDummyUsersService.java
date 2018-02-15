package com.shutafin;

import com.google.gson.Gson;
import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class GenerateDummyUsersService {

    private static final String URL_REGISTRATION = "http://localhost:8080/api/users/registration/request";
    private static final String URL_ACCOUNT_STATUS = "http://localhost:8000/users/account-status?status=";
    private static final String URL_MATCHING = "http://localhost:8300/matching/";
    private static final String URL_MATCHING_EXTENDED = "http://localhost:8300/matching/extended/";
    private static final int COUNT_CHARACTER_NAME = 3;
    private static final String PASSWORD = "11111111";
    private static final Integer USER_LANGUAGE_ID = 1;
    private static final String EMAIL_BEGIN = "tel";
    private static final String EMAIL_END = "@ran";

    private Random random = new Random();

    public void run(int countUsers, int countThreads) {

        long start = System.currentTimeMillis();

        int newCountThreads = countUsers < countThreads ? countUsers : countThreads;

        int countUsersInThread = countUsers / newCountThreads;

        ExecutorService service = Executors.newFixedThreadPool(newCountThreads);
        List<Future<?>> futureList = new ArrayList<>();
        for (int i = 0; i < newCountThreads; i++) {
            int userIdFrom = i * countUsersInThread + 1;
            int userIdTo = (i == newCountThreads - 1) ? countUsers + 1 : userIdFrom + countUsersInThread;
            futureList.add(service.submit(() -> {
                addUsers(userIdFrom, userIdTo);
                updateUserAccountStatus(userIdFrom, userIdTo);
                addQuestionsMain(userIdFrom, userIdTo);
                addQuestionsExtended(userIdFrom, userIdTo);
            }));
        }
        for (Future<?> future : futureList) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        long finish = System.currentTimeMillis();
        System.out.println("Finish in " + (finish - start) + " ms");
        service.shutdown();
    }

    private void sendRequest(Object object, String url, HttpMethod httpMethod) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity;
        if (object == null) {
            entity = new HttpEntity<>(headers);
        } else {
            entity = new HttpEntity<>(new Gson().toJson(object), headers);
        }
        restTemplate.exchange(url, httpMethod, entity, Void.class);
    }

    private void addUsers(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            sendRequest(getRegistrationRequestWeb(i), URL_REGISTRATION, HttpMethod.POST);
            System.out.println("Create user Id=" + i + " - Ok!");
        }
    }

    private AccountRegistrationRequest getRegistrationRequestWeb(int n) {
        return AccountRegistrationRequest.builder()
                .firstName(getName(COUNT_CHARACTER_NAME))
                .lastName(getName(COUNT_CHARACTER_NAME))
                .email(getEmail(n))
                .password(PASSWORD)
                .userLanguageId(USER_LANGUAGE_ID)
                .build();
    }

    private String getName(int countChars) {
        StringBuilder name = new StringBuilder(countChars);
        for (int i = 0; i < countChars; i++) {
            name.append(getChar());
        }
        return name.toString();
    }

    private char getChar() {
        return (char) (random.nextInt('z' - 'a' + 1) + 'a');
    }

    private String getEmail(int number) {
        return EMAIL_BEGIN + Integer.toString(number) + EMAIL_END;
    }

    private void addQuestionsMain(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            sendRequest(getListUserQuestionAnswerDTO(), URL_MATCHING + i, HttpMethod.POST);
            System.out.println("Add main questions userId=" + i + " - save Ok!");
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

    private void updateUserAccountStatus(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            String url = URL_ACCOUNT_STATUS + AccountStatus.COMPLETED_REQUIRED_MATCHING.getCode() + "&userId=" + i;
            sendRequest(null, url, HttpMethod.GET);
            System.out.println("Update UserAccountStatus userId=" + i + " - Ok!");
        }
    }

    private void addQuestionsExtended(int userIdFrom, int userIdTo) {
        for (int i = userIdFrom; i < userIdTo; i++) {
            sendRequest(getListUserQuestionExtendedAnswersWeb(), URL_MATCHING_EXTENDED + i, HttpMethod.POST);
            System.out.println("Save questionsExtended userId=" + i + " - Ok!");
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


