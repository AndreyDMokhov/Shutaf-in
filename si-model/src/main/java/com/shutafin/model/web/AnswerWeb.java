package com.shutafin.model.web;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * Created by evgeny on 8/23/2017.
 */
public class AnswerWeb implements DataResponse {
    @Min(value = 1)
    private Integer answerId;

    @NotBlank
    private String description;

    @NotBlank
    private Boolean isUniversal;

    public AnswerWeb() {
    }

    public AnswerWeb(Integer answerId, String description, Boolean isUniversal) {
        this.answerId = answerId;
        this.description = description;
        this.isUniversal = isUniversal;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUniversal() {
        return isUniversal;
    }

    public void setUniversal(Boolean universal) {
        isUniversal = universal;
    }
}
