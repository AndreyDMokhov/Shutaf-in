package com.shutafin.repository;

import com.shutafin.model.entities.UserQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuestionAnswerRepository extends JpaRepository<UserQuestionAnswer, Long>, CustomUserQuestionAnswerRepository {
    void deleteAllByUserId(Long userId);

}
