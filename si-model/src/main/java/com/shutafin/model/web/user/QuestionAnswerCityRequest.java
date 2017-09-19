package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by evgeny on 9/17/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionAnswerCityRequest {
    @NotNull
    private Integer questionId;
    @NotNull
    private Integer cityId;
}
