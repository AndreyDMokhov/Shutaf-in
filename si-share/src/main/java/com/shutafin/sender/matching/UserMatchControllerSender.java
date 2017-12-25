package com.shutafin.sender.matching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.matching.MatchingQuestionsSelectedAnswersDTO;
import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;
import com.shutafin.model.web.matching.UserQuestionAnswerDTO;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserMatchControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public List<Long> getMatchingUsers(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/search/%d", userId);

        return new ObjectMapper().convertValue(new RestTemplate().getForEntity(url, List.class).getBody(), new TypeReference<List<Long>>() {});
    }

    public void saveSelectedUserQuestionsAnswers(Long userId, List<UserQuestionAnswerDTO> questionsAnswers) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/save/%d", userId);
        new RestTemplate().postForEntity(url, questionsAnswers, Void.class);
    }

    public List<QuestionsListWithAnswersDTO> getQuestionsAnswersByLanguageId(Integer languageId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/questionnaire/initialization/%d", languageId);
        return new RestTemplate().getForEntity(url,List.class).getBody();
    }

    public List<MatchingQuestionsSelectedAnswersDTO> getSelectedUserQuestionsAnswers(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/questionnaire/answers/%d", userId);
        return new RestTemplate().getForEntity(url, List.class).getBody();
    }
}
