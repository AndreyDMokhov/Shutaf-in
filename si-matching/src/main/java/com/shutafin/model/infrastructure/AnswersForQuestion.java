package com.shutafin.model.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswersForQuestion {
    private Integer answerId;
    private String description;
    private Boolean isUniversal;
}
