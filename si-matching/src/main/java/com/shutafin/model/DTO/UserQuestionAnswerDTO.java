package com.shutafin.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserQuestionAnswerDTO {
    @NotNull
    private Integer questionId;
    @NotNull
    private Integer answerId;
}
