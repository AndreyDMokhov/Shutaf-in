package com.shutafin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQuestionExtendedAnswersWeb {

    private Integer questionId;
    private Integer questionImportanceId;
    private List<Integer> answersId;

}
