package com.shutafin.model.web.matching;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Edward Kats
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MatchingInitializationResponse {
    private List<QuestionsListWithAnswersDTO> questionAnswersResponses;
    private List<MatchingQuestionsSelectedAnswersDTO> selectedAnswersResponses;
    private List<QuestionExtendedWithAnswersLocaleWeb> questionExtendedWithAnswers;
    private List<QuestionImportanceDTO> questionImportanceList;
    private List<UserQuestionExtendedAnswersWeb> selectedExtendedAnswersResponses;
    private Boolean isMatchingEnabled;
}
