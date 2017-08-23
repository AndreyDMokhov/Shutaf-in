package com.shutafin.model.web;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by evgeny on 8/23/2017.
 */
public class QuestionWeb implements DataResponse {
    @Min(value = 1)
    private Integer questionId;

    @NotBlank
    private String description;

    @NotBlank
    private Boolean isActive;

    private List<AnswerWeb> answers;

    private List<Integer> selectedAnswersIds;

    public QuestionWeb() {
    }

    public QuestionWeb(Integer questionId, String description, Boolean isActive, List<AnswerWeb> answers, List<Integer> selectedAnswersIds) {
        this.questionId = questionId;
        this.description = description;
        this.isActive = isActive;
        this.answers = answers;
        this.selectedAnswersIds = selectedAnswersIds;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<AnswerWeb> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerWeb> answers) {
        this.answers = answers;
    }

    public List<Integer> getSelectedAnswersIds() {
        return selectedAnswersIds;
    }

    public void setSelectedAnswersIds(List<Integer> selectedAnswersIds) {
        this.selectedAnswersIds = selectedAnswersIds;
    }
}
