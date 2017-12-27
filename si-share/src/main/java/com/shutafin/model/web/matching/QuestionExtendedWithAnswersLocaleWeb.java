package com.shutafin.model.web.matching;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionExtendedWithAnswersLocaleWeb {

    private Integer questionId;
    private String questionDescription;
    private Map<Integer, String> answers;

}
