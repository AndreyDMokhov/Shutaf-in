package com.shutafin.model.web.initialization;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionAnswerElement {

    private Integer questionId;
    private Integer answerId;
    private String description;
    private Boolean isUniversal;
}
