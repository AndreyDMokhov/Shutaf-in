ALTER TABLE `userdb`.`i_question`
ADD COLUMN `QUESTION_TYPE` INT(11) NOT NULL DEFAULT 1 AFTER `IS_ACTIVE`;

INSERT INTO `I_QUESTION` (`ID`,`IS_ACTIVE`,`QUESTION_TYPE`) VALUES(5,TRUE,3);
INSERT INTO `I_QUESTION` (`ID`,`IS_ACTIVE`,`QUESTION_TYPE`) VALUES(6,TRUE,2);