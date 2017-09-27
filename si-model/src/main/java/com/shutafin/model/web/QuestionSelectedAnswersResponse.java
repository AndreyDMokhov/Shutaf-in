package com.shutafin.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by evgeny on 9/12/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionSelectedAnswersResponse {
    private Integer questionId;
    private List<Integer> selectedAnswersIds;
}
