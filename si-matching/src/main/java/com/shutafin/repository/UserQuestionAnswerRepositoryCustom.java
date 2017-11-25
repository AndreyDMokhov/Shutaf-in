package com.shutafin.repository;

import com.shutafin.model.dto.UserQuestionAnswerDTO;
import com.shutafin.model.infrastructure.SelectedAnswerElement;

import java.util.List;

public interface UserQuestionAnswerRepositoryCustom {
    List<SelectedAnswerElement> findAllByUserId(Long userId);
    void saveList(Long userId, List<UserQuestionAnswerDTO> questionsAnswers);
}
