package com.shutafin.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionExtendedWithAnswersLocaleWeb {

    private Integer questionId;
    private String questionDescription;
    private Map<Integer, String> answers;

}
