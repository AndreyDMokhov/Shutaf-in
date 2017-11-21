package com.shutafin.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionsListWithSelectedAnswersDTO {
    private Integer questionId;
    private List<Integer> selectedAnswersIds;
}
