use `si_matching`;

-- Create extended question
SELECT @question_id := MAX(i_question_extended.ID) + 1
FROM i_question_extended;

INSERT INTO i_question_extended (ID) VALUES (@question_id);


INSERT INTO i_question_extended_locale (DESCRIPTION, LANGUAGE_ID, QUESTION_ID) VALUES ('Extended question number 1', 1, @question_id);
INSERT INTO i_question_extended_locale (DESCRIPTION, LANGUAGE_ID, QUESTION_ID) VALUES ('Дополнительный вопрос номер 1', 2, @question_id);


-- Create answer 1 on extended question 1
SELECT @answer_1_id := MAX(i_answer_extended.ID) + 1
FROM i_answer_extended;

INSERT INTO i_answer_extended (ID, QUESTION_ID) VALUES (@answer_1_id, @question_id);

INSERT INTO i_answer_extended_locale (LANGUAGE_ID, DESCRIPTION, ANSWER_ID) VALUES (1, 'Answer number 1 on extended question 1', @answer_1_id);
INSERT INTO i_answer_extended_locale (LANGUAGE_ID, DESCRIPTION, ANSWER_ID) VALUES (2, 'Ответ номер 1 на дополнительный вопрос номер 1', @answer_1_id);


-- Create answer 2 on question 1
SELECT @answer_2_id := MAX(i_answer_extended.ID) + 1
FROM i_answer_extended;

INSERT INTO i_answer_extended (ID, QUESTION_ID) VALUES (@answer_2_id, @question_id);

INSERT INTO i_answer_extended_locale (LANGUAGE_ID, DESCRIPTION, ANSWER_ID) VALUES (1, 'Answer number 2 on extended question 1', @answer_2_id);
INSERT INTO i_answer_extended_locale (LANGUAGE_ID, DESCRIPTION, ANSWER_ID) VALUES (2, 'Ответ номер 2 на дополнительный вопрос номер 1', @answer_2_id);


-- Define answers similarity
SELECT @similarity_answer_id := MAX(answer_similarity.ID) + 1
FROM answer_similarity;

SET @similarity_score = 1; -- 4 => 0 "Irrelevant" , 3 => 1 "A little important" , 2 => 10 "Somewhat important" ,  1 => 100 "Very important"
INSERT INTO answer_similarity (ID, ANSWER_ID, ANSWER_TO_COMPARE_ID, SIMILARITY_SCORE) VALUES (@similarity_answer_id, @answer_1_id, @answer_1_id, @similarity_score);



SELECT @similarity_answer_id := MAX(answer_similarity.ID) + 1
FROM answer_similarity;

SET @similarity_score = 2; -- 4 = 0, 3 = 1, 2 = 10, 1 = 100
INSERT INTO answer_similarity (ID, ANSWER_ID, ANSWER_TO_COMPARE_ID, SIMILARITY_SCORE) VALUES (@similarity_answer_id, @answer_1_id, @answer_2_id, @similarity_score);



SELECT @similarity_answer_id := MAX(answer_similarity.ID) + 1
FROM answer_similarity;

SET @similarity_score = 1; -- 4 = 0, 3 = 1, 2 = 10, 1 = 100
INSERT INTO answer_similarity (ID, ANSWER_ID, ANSWER_TO_COMPARE_ID, SIMILARITY_SCORE) VALUES (@similarity_answer_id, @answer_2_id, @answer_2_id, @similarity_score);



SELECT @similarity_answer_id := MAX(answer_similarity.ID) + 1
FROM answer_similarity;

SET @similarity_score = 2; -- 4 = 0, 3 = 1, 2 = 10, 1 = 100
INSERT INTO answer_similarity (ID, ANSWER_ID, ANSWER_TO_COMPARE_ID, SIMILARITY_SCORE) VALUES (@similarity_answer_id, @answer_2_id, @answer_1_id, @similarity_score);
