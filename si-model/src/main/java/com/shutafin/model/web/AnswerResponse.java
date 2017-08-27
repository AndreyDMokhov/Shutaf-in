package com.shutafin.model.web;

import lombok.*;

/**
 * Created by evgeny on 8/23/2017.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerResponse implements DataResponse {
    private Integer answerId;
    private String description;
    private Boolean isUniversal;
}
