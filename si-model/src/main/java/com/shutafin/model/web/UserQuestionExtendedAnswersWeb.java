package com.shutafin.model.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserQuestionExtendedAnswersWeb {

    private Integer questionId;
    private Integer questionImportanceId;
    private List<Integer> answersId;

}
