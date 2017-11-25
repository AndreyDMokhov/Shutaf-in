package com.shutafin.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQuestionExtendedAnswersWeb {

    private Integer questionId;
    private Integer questionImportanceId;
    private List<Integer> answersId;

}
