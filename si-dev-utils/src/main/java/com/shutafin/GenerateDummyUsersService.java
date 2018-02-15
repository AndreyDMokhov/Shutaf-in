package com.shutafin;

import com.google.gson.Gson;
import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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


@Configuration
@ComponentScan("com.shutafin")
class DummyUserRunnerApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.shutafin");
        context.register(DummyUserRunnerApplication.class);
        context.getBean(GenerateDummyUsersService.class).run();
    }

    @Bean
    public DiscoveryClient discoveryClient() {
        return new SimpleDiscoveryClient(new SimpleDiscoveryProperties());
    }

}

@Service
public class GenerateDummyUsersService {

    private static Random random = new Random();

    public void run() {
        addUsers(1000);

        addQuestions(1, 1000);

        System.out.println("End!!!");
    }

    private void addUsers(int count) {
        String url = "http://localhost:8080/api/users/registration/request";
        Gson gson = new Gson();

        for (int i = 1; i <= count; i++) {
            AccountRegistrationRequest registrationRequestWeb = getRegistrationRequestWeb(i);
            RestTemplate restTemplate = new RestTemplate();
            String json = gson.toJson(registrationRequestWeb);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);

            restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
            System.out.println(i + " - Ok!");
        }
    }

    private void addQuestions(int userIdFrom, int userIdTo) {

        Gson gson = new Gson();

        for (int i = userIdFrom; i <= userIdTo; i++) {
            addQuestionsMain(i, gson);
            updateUserAccountStatus(i);
            addQuestionsExtended(i, gson);
        }
    }

    private void addQuestionsMain(int i, Gson gson) {
        String url = "http://localhost:8300/matching/" + i;
        List<UserQuestionAnswerDTO> userQuestionAnswerDTOList = getListUserQuestionAnswerDTO();
        RestTemplate restTemplate = new RestTemplate();
        String json = gson.toJson(userQuestionAnswerDTOList);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        System.out.println(i + " - saveQuestionsMain Ok!");
    }

    private List<UserQuestionAnswerDTO> getListUserQuestionAnswerDTO() {
        List<UserQuestionAnswerDTO> userQuestionAnswerDTOList = new ArrayList<UserQuestionAnswerDTO>(4);
        UserQuestionAnswerDTO userQuestionAnswerDTO;
        for (int i = 1; i <= 4; i++) {
            userQuestionAnswerDTO = new UserQuestionAnswerDTO();
            userQuestionAnswerDTO.setQuestionId(i);
            if (i == 4) {
                userQuestionAnswerDTO.setAnswerId(11);
            } else if (i == 1) {
                userQuestionAnswerDTO.setAnswerId(i);
            } else {
                userQuestionAnswerDTO.setAnswerId(2 * i);
            }
            userQuestionAnswerDTOList.add(userQuestionAnswerDTO);
        }
        return userQuestionAnswerDTOList;
    }

    private void updateUserAccountStatus(int i) {
        String url = "http://localhost:8000/users/account-status?status=" + AccountStatus.COMPLETED_REQUIRED_MATCHING.getCode() + "&userId=" + i;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        restTemplate.exchange(url, HttpMethod.GET, entity, Void.class);
        System.out.println(i + " - updateUserAccountStatus Ok!");
    }

    private void addQuestionsExtended(int i, Gson gson) {
        String url = "http://localhost:8300/matching/extended/" + i;
        List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList = getListUserQuestionExtendedAnswersWeb();
        RestTemplate restTemplate = new RestTemplate();
        String json = gson.toJson(userQuestionExtendedAnswersWebList);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);

        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
        System.out.println(i + " - saveQuestionsExtended Ok!");
    }

    private List<UserQuestionExtendedAnswersWeb> getListUserQuestionExtendedAnswersWeb() {
        List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList = new ArrayList<UserQuestionExtendedAnswersWeb>(4);
        UserQuestionExtendedAnswersWeb userQuestionExtendedAnswersWeb;
        for (int i = 1; i <= 4; i++) {
            userQuestionExtendedAnswersWeb = new UserQuestionExtendedAnswersWeb();
            userQuestionExtendedAnswersWeb.setQuestionId(i);
            if (i == 1) {
                userQuestionExtendedAnswersWeb.setAnswersId(Arrays.asList(i));
            } else if (i == 2) {
                userQuestionExtendedAnswersWeb.setAnswersId(Arrays.asList(5));
            } else {
                userQuestionExtendedAnswersWeb.setAnswersId(Arrays.asList(i * 3));
            }
            userQuestionExtendedAnswersWeb.setQuestionImportanceId(1);
            userQuestionExtendedAnswersWebList.add(userQuestionExtendedAnswersWeb);
        }
        return userQuestionExtendedAnswersWebList;
    }

    private AccountRegistrationRequest getRegistrationRequestWeb(int n) {

        AccountRegistrationRequest registrationRequestWeb = new AccountRegistrationRequest();

        registrationRequestWeb.setFirstName(getName(3));
        registrationRequestWeb.setLastName(getName(3));
        registrationRequestWeb.setEmail(getEmail(n));
        registrationRequestWeb.setPassword("11111111");
        registrationRequestWeb.setUserLanguageId(1);

        return registrationRequestWeb;
    }

    private String getName(int c) {
        StringBuilder name = new StringBuilder(c);
        for (int i = 0; i < c; i++) {
            name.append(getChar());
        }
        return name.toString();
    }

    private char getChar() {
        return (char) (random.nextInt(26) + 'a');
    }

    private String getEmail(int n) {
        return "tel" + Integer.toString(n) + "@ran";
    }

}


