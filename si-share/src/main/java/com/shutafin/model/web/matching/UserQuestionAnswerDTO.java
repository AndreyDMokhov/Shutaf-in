package com.shutafin.model.web.matching;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserQuestionAnswerDTO {
    @NotNull
    private Integer questionId;
    @NotNull
    private Integer answerId;
}
