package com.shutafin.model.web.matching;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatchingQuestionsSelectedAnswersDTO {
    private Integer questionId;
    private List<Integer> selectedAnswersIds;
}
