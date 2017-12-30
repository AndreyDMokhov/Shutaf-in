package com.shutafin.sender.matching;


import com.shutafin.model.exception.APIExceptionClient;
import com.shutafin.model.web.matching.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.web.matching.QuestionImportanceDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class QuestionExtendedControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(Integer languageId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                    String.format("/matching/extended/questions/answers/%d", languageId);
            return new RestTemplate().getForEntity(url, List.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public List<QuestionImportanceDTO> getQuestionImportanceList(Integer languageId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                    String.format("/matching/extended/questions/importance/%d", languageId);
            return new RestTemplate().getForEntity(url, List.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }

    public List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(Long userId) {
        try {
            String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                    String.format("/matching/extended/questions/selected/answers/%d", userId);
            return new RestTemplate().getForEntity(url, List.class).getBody();
        } catch (HttpClientErrorException e) {
            throw APIExceptionClient.getException(e);
        }
    }
}
