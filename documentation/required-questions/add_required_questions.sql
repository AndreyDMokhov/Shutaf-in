use `si_matching`;

-- Create question
SELECT @question_id := MAX(i_question.ID) + 1
FROM i_question;

SET @is_question_active = 1; -- 1 = active/shown, 0 = NOT active/shown
INSERT INTO i_question (ID, IS_ACTIVE) VALUES (@question_id, 1);


INSERT INTO i_question_locale (DESCRIPTION, LANGUAGE_ID, QUESTION_ID) VALUES ('Question number 1', 1, @question_id);
INSERT INTO i_question_locale (DESCRIPTION, LANGUAGE_ID, QUESTION_ID) VALUES ('Вопрос номер 1', 2, @question_id);


-- Create answer 1 on question 1
SELECT @answer_id := MAX(i_answer.ID) + 1
FROM i_answer;

SET @is_universal_answer := 0; -- 1 = universal, 0 = NOT universal

INSERT INTO i_answer (ANSWER_ID, IS_UNIVERSAL, QUESTION_ID) VALUES (@answer_id, @is_universal_answer, @question_id);

INSERT INTO i_answer_locale (LANGUAGE_ID, DESCRIPTION, ANSWER_ID) VALUES (1, 'Answer number 1', @answer_id);
INSERT INTO i_answer_locale (LANGUAGE_ID, DESCRIPTION, ANSWER_ID) VALUES (2, 'Ответ номер 1', @answer_id);


-- Create answer 2 on question 1
SELECT @answer_id := MAX(i_answer.ID) + 1
FROM i_answer;

SET @is_universal_answer := 1; -- 1 = universal, 0 = NOT universal

INSERT INTO i_answer (ANSWER_ID, IS_UNIVERSAL, QUESTION_ID) VALUES (@answer_id, @is_universal_answer, @question_id);

INSERT INTO i_answer_locale (LANGUAGE_ID, DESCRIPTION, ANSWER_ID) VALUES (1, 'Answer number 2', @answer_id);
INSERT INTO i_answer_locale (LANGUAGE_ID, DESCRIPTION, ANSWER_ID) VALUES (2, 'Ответ номер 2', @answer_id);
