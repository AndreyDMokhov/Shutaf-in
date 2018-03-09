package com.shutafin.model.web.matching;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserQuestionExtendedAnswersWeb {

    private Integer questionId;
    private Integer questionImportanceId;
    private List<Integer> answersId;

}
