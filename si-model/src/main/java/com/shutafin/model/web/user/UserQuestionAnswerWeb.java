package com.shutafin.model.web.user;

import javax.validation.constraints.NotNull;

/**
 * Created by evgeny on 8/22/2017.
 */
public class UserQuestionAnswerWeb {
    @NotNull
    private Long userId;
    @NotNull
    private Integer questionId;
    @NotNull
    private Integer answerId;

    public UserQuestionAnswerWeb() {
    }

    public UserQuestionAnswerWeb(Long userId, Integer questionId, Integer answerId) {
        this.userId = userId;
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }
}
