package com.shutafin.model.web;

import lombok.*;
import java.util.List;

/**
 * Created by evgeny on 8/23/2017.
 */
@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionAnswersResponse {
    private Integer questionId;
    private String description;
    private Boolean isActive;

    private List<AnswerResponse> answers;

    public QuestionAnswersResponse(Integer questionId, String description, Boolean isActive) {
        this.questionId = questionId;
        this.description = description;
        this.isActive = isActive;
    }
}
