package com.shutafin.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by evgeny on 8/23/2017.
 */
@Deprecated
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerResponse {
    private Integer answerId;
    private String description;
    private Boolean isUniversal;
}
