package com.shutafin.model.web;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by evgeny on 8/23/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionResponse {
    private Integer questionId;
    private String description;
    private Boolean isActive;

    private List<AnswerResponse> answers;

    private List<Integer> selectedAnswersIds;

}
