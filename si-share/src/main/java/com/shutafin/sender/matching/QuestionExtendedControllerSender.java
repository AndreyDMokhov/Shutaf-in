package com.shutafin.sender.matching;


import com.shutafin.model.web.matching.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.web.matching.QuestionImportanceDTO;
import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.route.DiscoveryRoutingService;
import com.shutafin.route.RouteDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class QuestionExtendedControllerSender {

    @Autowired
    private DiscoveryRoutingService routingService;

    public List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(Integer languageId){
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/questions/answers/%d", languageId);

        return new RestTemplate().getForEntity(url, List.class).getBody();
    }

    public List<QuestionImportanceDTO> getQuestionImportanceList(Integer languageId){
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/questions/importance/%d", languageId);

        return new RestTemplate().getForEntity(url, List.class).getBody();
    }

    public List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(Long userId){
        String url = routingService.getRoute(RouteDirection.SI_MATCHING) +
                String.format("/matching/extended/questions/selected/answers/%d", userId);

        return new RestTemplate().getForEntity(url, List.class).getBody();
    }
}
