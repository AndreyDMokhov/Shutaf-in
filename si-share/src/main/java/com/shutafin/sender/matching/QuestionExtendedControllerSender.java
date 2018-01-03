package com.shutafin.sender.matching;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shutafin.model.web.matching.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.web.matching.QuestionImportanceDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class QuestionExtendedControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;
    
    
    @Autowired
    private RestTemplate restTemplate;

    @SneakyThrows
    public List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(Integer languageId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/questions/answers/%d", languageId);

        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<QuestionExtendedWithAnswersLocaleWeb>>() {});
    }

    @SneakyThrows
    public List<QuestionImportanceDTO> getQuestionImportanceList(Integer languageId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/questions/importance/%d", languageId);

        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();

        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<QuestionImportanceDTO>>() {});
    }

    @SneakyThrows
    public List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(Long userId) {
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/questions/selected/answers/%d", userId);
        String jsonBody = restTemplate.getForEntity(url, String.class).getBody();
        return new ObjectMapper().readValue(jsonBody, new TypeReference<List<UserQuestionExtendedAnswersWeb>>() {});
    }
}
