package com.shutafin.model.web.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by evgeny on 8/22/2017.
 */
@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionAnswerRequest {
    @NotNull
    private Integer questionId;
    @NotNull
    private Integer answerId;
}
