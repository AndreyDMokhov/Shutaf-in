package com.shutafin.model.DTO;

import com.shutafin.model.infrastructure.AnswersForQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionsListWithAnswersDTO {
    private Integer questionId;
    private String description;
    private Boolean isActive;

    private List<AnswersForQuestion> answers;

    public QuestionsListWithAnswersDTO(Integer questionId, String description, Boolean isActive) {
        this.questionId = questionId;
        this.description = description;
        this.isActive = isActive;
    }
}
