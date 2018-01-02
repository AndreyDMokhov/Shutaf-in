package com.shutafin.sender.matching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.matching.MatchingQuestionsSelectedAnswersDTO;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserMatchControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    public List<Long> getMatchingUsers(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/search/%d", userId);
        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<Long>>() { });
    }

    public void saveSelectedUserQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/save/%d", userId);
        restTemplate.postForEntity(url, questionsAnswers, Void.class);
    }

    @SneakyThrows
    public List<QuestionsListWithAnswersDTO> getQuestionsAnswersByLanguageId(Integer languageId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/questionnaire/initialization/%d", languageId);
        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<QuestionsListWithAnswersDTO>>() {});
    }

    @SneakyThrows
    public List<MatchingQuestionsSelectedAnswersDTO> getSelectedUserQuestionsAnswers(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/questionnaire/answers/%d", userId);

        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<MatchingQuestionsSelectedAnswersDTO>>() {});
    }
}
